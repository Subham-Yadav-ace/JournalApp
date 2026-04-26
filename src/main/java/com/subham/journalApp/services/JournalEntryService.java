package com.subham.journalApp.services;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component

public class JournalEntryService {
            @Autowired
            private JournalEntryRepository journalEntryRepository;//now we can use the mongocode for the given entity


            //save ka method
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
    public void deleteEntry(ObjectId id){
             journalEntryRepository.deleteById(id);
    }



}
