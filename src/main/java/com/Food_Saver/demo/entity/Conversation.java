package com.Food_Saver.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.timeStamp = LocalDateTime.now();
    }

}
