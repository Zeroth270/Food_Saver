package com.Food_Saver.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long foodId;

    private String title;
    private String description;
    private String foodType; // Veg or NonVeg
    private String quantity;
    private double price;

    @Column(name = "is_free", nullable = false, columnDefinition = "bit default 0")
    private boolean free;

    private Date availableTill;

    private String location;

    private double latitude;

    private double longitude;

    @Enumerated(EnumType.STRING)
    private FoodStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({ "password", "createdAt" })
    private User user;

    @OneToMany(mappedBy = "food",
            cascade = CascadeType.ALL,
            orphanRemoval = true)

    @JsonManagedReference
    private List<Contact> contacts;

}
