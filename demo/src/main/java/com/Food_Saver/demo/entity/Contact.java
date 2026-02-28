package com.Food_Saver.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String message;

    private String status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food foodPostId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User receiver;



}
