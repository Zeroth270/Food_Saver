package com.Food_Saver.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {

    private Long conversationId;
    private String partnerEmail;
    private String lastMessage;
    private LocalDateTime timestamp;
    private String partnerName;
    private String foodTitle;
    private String foodPostId;

}
