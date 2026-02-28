package com.Food_Saver.demo.service;

import com.Food_Saver.demo.dto.LoggingUserDto;
import com.Food_Saver.demo.dto.RegisterUserDto;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    public User registerUser(RegisterUserDto register) {
        userRepo.findByEmail(register.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Email Already Exists!!");
        });

        User user = new User();
        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());
        user.setRole(register.getRole());

        return userRepo.save(user);
    }

    public User loginUser(LoggingUserDto login) {
        User user = userRepo.findByEmail(login.getEmail())
                .orElseThrow(() -> new RuntimeException("Email Not Found!!"));

        if (!login.getPassword().equals(user.getPassword())){
            throw new RuntimeException("Password Not Match!!");
        }

        return user;
    }

}


