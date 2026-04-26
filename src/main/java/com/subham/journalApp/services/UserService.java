package com.subham.journalApp.services;

import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.entity.User;
import com.subham.journalApp.repository.JournalEntryRepository;
import com.subham.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;//now we can use the mongocode for the given entity


    //save ka method
    public  void saveEntry(User user){
        userRepository.save(user);
    }
    //fetch ka method
    public List<User> getData(){
        return  userRepository.findAll();
    }
    //fetch by id
    public Optional<User> getByID(ObjectId id){
        return userRepository.findById(id);

    }
    public void deleteEntry(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
}
