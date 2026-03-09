package com.Food_Saver.demo.service;

import com.Food_Saver.demo.entity.Food;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.repository.FoodRepo;
import com.Food_Saver.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private UserRepo userRepo;

    public Food saveFoodDetail(Food food) {
        // The user is already set from the JSON body (frontend sends user: { userId:
        // ... })
        if (food.getUser() == null || food.getUser().getUserId() == 0) {
            throw new RuntimeException("User is required!");
        }
        User user = userRepo.findById(food.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found!"));
        food.setUser(user);
        return foodRepo.save(food);
    }

    // get single food by foodId
    public Food getFoodById(Long id) {
        return foodRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found!"));
    }

    // get all food by userId
    public List<Food> getFoodByUserId(Long userId) {
        List<Food> foods = foodRepo.findByUserId(userId);
        if (foods.isEmpty()) {
            throw new RuntimeException("No food found for this user!");
        }
        return foods;
    }

    public void deleteFoodById(Long id) {
        if (!foodRepo.existsById(id)) {
            throw new RuntimeException("Food Not Found Of This Id!");
        }
        foodRepo.deleteById(id);
    }

    public Food updateFoodList(Long id, Food newFood) {
        Food exitingFood = foodRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Food Not Found Of This Id!!"));

        exitingFood.setTitle(newFood.getTitle());
        exitingFood.setDescription(newFood.getDescription());
        exitingFood.setFoodType(newFood.getFoodType());
        exitingFood.setQuantity(newFood.getQuantity());
        exitingFood.setPrice(newFood.getPrice());
        exitingFood.setFree(newFood.isFree()); // Lombok generates isFree() for boolean 'free'
        exitingFood.setAvailableTill(newFood.getAvailableTill());
        exitingFood.setLocation(newFood.getLocation());
        exitingFood.setStatus(newFood.getStatus());

        return foodRepo.save(exitingFood);
    }

    public List<Food>  discoverNearbyFood(double lat, double lng, double radius) {
        return foodRepo.findNearbyFood(lat, lng, radius);
    }

    public List<Food> smartSearch(
            String location,
            String foodType,
            Boolean freeOnly,
            Double lat,
            Double lng,
            Double radius
    ) {

        // If radius is null but lat/lng provided → default 5km
        if (lat != null && lng != null && radius == null) {
            radius = 5.0;
        }

        return foodRepo.advancedSearchWithRadius(
                location,
                foodType,
                freeOnly,
                lat,
                lng,
                radius
        );
    }
}
