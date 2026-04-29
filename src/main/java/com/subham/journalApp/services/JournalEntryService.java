package com.subham.journalApp.services;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.entity.User;
import com.subham.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component

public class JournalEntryService {
            @Autowired
            private JournalEntryRepository journalEntryRepository;//now we can use the mongocode for the given entity
    @Autowired
    private UserService userService;


    //save ka method
    @Transactional
        public  void saveEntry(JournalEntry journalEntry, String userName){
           try {
               User user=userService.findByUserName(userName);
               journalEntry.setDate(LocalDateTime.now());
               JournalEntry saved = journalEntryRepository.save(journalEntry);

               //here if the code break , it will get saved but in the user it doesnot get updaated
               user.getJournalEntries().add(saved);//first we saved the journal entries , then added it to the user's personal entries
               userService.saveEntry(user);//final update to the user's schema
           }
           catch (Exception e){
               System.out.println(e);
               throw new RuntimeException("Error saving entry");
           }
        }

    public  void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
        //fetch ka method
        public List<JournalEntry> getData(){
           return  journalEntryRepository.findAll();
        }
        //fetch by id
    public Optional<JournalEntry> getByID(ObjectId id){
         return journalEntryRepository.findById(id);

    }
    public void deleteEntry(ObjectId id,String userName){
         User user=userService.findByUserName(userName);
         user.getJournalEntries().removeIf(x->x.getId().equals(id));
         //here we found the user which has the  journal entry of this id ,
        //we ran a search in the user's journal entries to find the entry equals to this id
        userService.saveEntry(user);
             journalEntryRepository.deleteById(id);
    }



}
