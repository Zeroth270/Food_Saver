package com.Food_Saver.demo.DTO;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String name;
    private String email;
    private String password;
    private String role;

}
