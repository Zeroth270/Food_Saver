package com.Food_Saver.demo.repository;

import com.Food_Saver.demo.entity.Contact;
import com.Food_Saver.demo.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContactRepo extends JpaRepository<Contact,Long> {

    @Query("SELECT f FROM Food f WHERE f.foodId = :foodId")
    Optional<Food> findByFoodId(Food foodId);

}

