package com.Food_Saver.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String message;

    @Enumerated(EnumType.STRING)
    private ContactStatus status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User receiver;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
