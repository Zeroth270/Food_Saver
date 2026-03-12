package com.Food_Saver.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    private String senderEmail;
    private String receiverEmail;

    @Column(length = 1000)
    private String content;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    private LocalDateTime timestamp;

    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

}
