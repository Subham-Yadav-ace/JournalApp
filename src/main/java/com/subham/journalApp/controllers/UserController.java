package com.subham.journalApp.controllers;


import com.subham.journalApp.entity.User;
import com.subham.journalApp.repository.UserRepository;
import com.subham.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService UserService;

    @GetMapping
    public List<User> getAllUser(){
        return UserService.getData();
    }
    @PostMapping
    public void createUser(@RequestBody  User user){
        UserService.saveEntry(user);
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody  User user,@PathVariable String userName){
        User userInDB = UserService.findByUserName(userName);
        if(userInDB!=null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            UserService.saveEntry(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
