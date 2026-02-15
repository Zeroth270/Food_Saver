package com.Food_Saver.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String message;

    private String status;

    private Date createdAt;

    @ManyToOne
    private Food foodPostId;

    @ManyToOne
    private User receiver;



}
