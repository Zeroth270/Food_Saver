package com.Food_Saver.demo.repository;

import com.Food_Saver.demo.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepo extends JpaRepository<Food,Long> {

}
