package com.subham.journalApp.controllers;


import com.subham.journalApp.entity.JournalEntry;
import com.subham.journalApp.entity.User;
import com.subham.journalApp.services.JournalEntryService;
import com.subham.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);

        List<JournalEntry>All=user.getJournalEntries();//get cmd from the lombok
           if(All!=null)return new ResponseEntity<>(All ,HttpStatus.OK);

           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("{userName}")
    public ResponseEntity<?>createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
        try {

            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.getByID(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{thisId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId thisId,@PathVariable String  userName){
            journalEntryService.deleteEntry(thisId,userName);
            String res="req has been performed successfully";
            return new  ResponseEntity<>(res,HttpStatus.OK);
        }

    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry,@PathVariable String  userName   ){
            JournalEntry old=journalEntryService.getByID(myId).orElse(null);

            if(old!=null){
                old.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("")?myEntry.getTitle(): old.getTitle() );
                old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")?myEntry.getContent():old.getContent());
                journalEntryService.saveEntry( old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


}
