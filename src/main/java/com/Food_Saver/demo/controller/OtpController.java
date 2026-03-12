package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.service.OtpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "Auth APIs", description = "User Operations")public class OtpController {

    @Autowired
    private OtpService emailService;

    private final Map<String, String> otpStorage = new HashMap<>();

    // Generate OTP
    public String generateOtp() {
        int otp = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    // Send OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {

        String otp = generateOtp();

        otpStorage.put(email, otp);

        emailService.sendOtp(email, otp);

        return "OTP sent successfully";
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {

        String storedOtp = otpStorage.get(email);

        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email);
            return "OTP verified successfully";
        }

        return "Invalid OTP";
    }

}
