package com.Food_Saver.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> otpExpiry = new ConcurrentHashMap<>();

    public void sendOtp(String email) {
        // Generate 6 digit OTP
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        // Store with 5 min expiry
        otpStore.put(email, otp);
        otpExpiry.put(email, LocalDateTime.now().plusMinutes(5));

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Food Saver - OTP Verification");
        message.setText("Your OTP is: " + otp + "\n\n5 min tak valid he jo karna he jaldi kar.\nOr kisi ke sath share  mat karna varna tere LAMBE wale lag jayenga.");
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String otp) {
        if (!otpStore.containsKey(email)) {
            throw new RuntimeException("OTP not found. Please request a new one.");
        }

        if (LocalDateTime.now().isAfter(otpExpiry.get(email))) {
            otpStore.remove(email);
            otpExpiry.remove(email);
            throw new RuntimeException("OTP expired. Please request a new one.");
        }

        boolean isValid = otp.equals(otpStore.get(email));

        if (isValid) {
            otpStore.remove(email);
            otpExpiry.remove(email);
        } else {
            throw new RuntimeException("Invalid OTP.");
        }

        return true;
    }

}
