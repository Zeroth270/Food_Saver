package com.Food_Saver.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;

    private String donorEmail;

    private String receiverEmail;

    private String foodPost;

    private LocalDateTime timeStamp;

    @PrePersist
    public void onCreate(){
        this.timeStamp = LocalDateTime.now();
    }

}
