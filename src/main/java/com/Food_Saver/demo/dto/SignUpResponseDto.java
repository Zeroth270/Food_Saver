package com.Food_Saver.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponseDto {

    Long userId;
    String userName;

}
