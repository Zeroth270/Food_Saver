package com.Food_Saver.demo.security;

import com.Food_Saver.demo.dto.*;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.entity.UserRole;
import com.Food_Saver.demo.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public SignUpResponseDto registerUser(RegisterUserDto registerDto) throws IllegalAccessException {
        Optional<User> user = userRepo.findByEmail(registerDto.getEmail());

        if  (user.isPresent()) {
             throw new IllegalAccessException("User already exists");
        }

         user = Optional.of(userRepo.save(
                 User.builder()
                         .userName(registerDto.getUserName())
                         .email(registerDto.getEmail())
                         .password(passwordEncoder.encode(registerDto.getPassword()))
                         .phoneNumber(registerDto.getPhoneNumber())
                         .role(UserRole.USER)
                         .build()));

        return new SignUpResponseDto(user.get().getUserId(), user.get().getUsername());

    }

    public LoginResponseDto loginUser(LoginRequestDto requestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(user.getUsername(), token);
    }

//    public User registerUser(RegisterUserDto register) {
//
//        userRepo.findByEmail(register.getEmail()).ifPresent(user -> {
//            throw new RuntimeException("Email Already Exists!!");
//        });
//
//        User user = new User();
//        user.setName(register.getUserName());
//        user.setEmail(register.getEmail());
//        user.setPhoneNumber(register.getPhoneNumber());
//        user.setPassword(register.getPassword());
//        try {
//            user.setRole(UserRole.valueOf(register.getRole().toUpperCase()));
//        } catch (IllegalArgumentException e){
//            throw new RuntimeException("Invalid Role!!");
//        }
//
//        return userRepo.save(user);
//    }
//
//    public User loginUser(LoggingUserDto login) {
//        User user = userRepo.findByEmail(login.getEmail())
//                .orElseThrow(() -> new RuntimeException("Email Not Found!!"));
//
//        if (!login.getPassword().equals(user.getPassword())){
//            throw new RuntimeException("Password Not Match!!");
//        }
//
//        return user;
//    }

}


