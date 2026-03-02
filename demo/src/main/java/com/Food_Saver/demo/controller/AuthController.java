package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.dto.LoggingUserDto;
import com.Food_Saver.demo.dto.OtpRequestDto;
import com.Food_Saver.demo.dto.RegisterUserDto;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.service.AuthService;
import com.Food_Saver.demo.service.OtpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Auth APIs", description = "User Operations")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

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

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequestDto request){
        otpService.sendOtp(request.getEmail());
        return ResponseEntity.ok("OTP sent to " + request.getEmail());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequestDto request) {
        otpService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok("OTP verified successfully");
    }

}
