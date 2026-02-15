package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.DTO.LoggingUserDto;
import com.Food_Saver.demo.DTO.RegisterUserDto;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Auth APIs", description = "User Operations")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto register){
        User registerUser = authService.registerUser(register);
        return ResponseEntity.status(HttpStatus.OK).body(registerUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoggingUserDto login){
        User user = authService.loginUser(login);
        return ResponseEntity.ok(user);
    }

}
