package com.Food_Saver.demo.service;

import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not FInd Of This ID :" + id));
    }

    public void deleteUserById(Long id) {
        User deleteUser =  userRepo.findById(id)
                .orElseThrow(() ->  new RuntimeException("User Not Found Of This Id!!"));
        userRepo.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User exitingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found Of This Id!!"));

        exitingUser.setName(updatedUser.getName());
        exitingUser.setEmail(updatedUser.getEmail());
        exitingUser.setPassword(updatedUser.getPassword());
        exitingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        exitingUser.setRole(updatedUser.getRole());
        exitingUser.setNotificationRadius(updatedUser.getNotificationRadius());
        exitingUser.setNotificationEnabled(updatedUser.isNotificationEnabled());

        return userRepo.save(exitingUser);
    }

}


