package com.Food_Saver.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long foodId;

    private String title;
    private String description;
    private String foodType; //Veg or NonVeg
    private String quantity;
    private double price;
    private boolean isFree;
    private Date availableTill;
    private String location;
    private String status; //AVAILABLE, EXPIRED, COMPLETED
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;


}
