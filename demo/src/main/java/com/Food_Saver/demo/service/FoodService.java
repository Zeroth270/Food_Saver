package com.Food_Saver.demo.service;

import com.Food_Saver.demo.entity.Food;
import com.Food_Saver.demo.repository.FoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    private FoodRepo foodRepo;

    public Food saveFoodDetail(Food food) {
        return foodRepo.save(food);
    }

    public Food getFoodDetail(Long id) {
       return foodRepo.findById(id).orElseThrow(()-> new RuntimeException("Food Detail Not Found!"));
    }

    public void deleteFoodById(Long id) {
        if (!foodRepo.existsById(id)){
            throw new RuntimeException("Food Not Found Of This Id!");
        }
        foodRepo.deleteById(id);
    }

    public Food updateFoodList(Long id,Food newFood) {
        Food exitingFood = foodRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food Not Found Of This Id!!"));

        exitingFood.setTitle(newFood.getTitle());
        exitingFood.setDescription(newFood.getDescription());
        exitingFood.setFoodType(newFood.getFoodType());
        exitingFood.setQuantity(newFood.getQuantity());
        exitingFood.setPrice(newFood.getPrice());
        exitingFood.setFree(newFood.isFree());
        exitingFood.setAvailableTill(newFood.getAvailableTill());
        exitingFood.setLocation(newFood.getLocation());
        exitingFood.setStatus(newFood.getStatus());

        return foodRepo.save(exitingFood);
    }

}


