package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.dto.LoginRequestDto;
import com.Food_Saver.demo.dto.RegisterUserDto;
import com.Food_Saver.demo.security.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth APIs", description = "User Operations")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerDto) throws IllegalAccessException {
        return ResponseEntity.ok(authService.registerUser(registerDto));
    }

    @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginDto) {
        return ResponseEntity.ok(authService.loginUser(loginDto));
    }

}
