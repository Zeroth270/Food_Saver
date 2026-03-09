package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.entity.Food;
import com.Food_Saver.demo.service.FoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@Tag(name = "Food APIs", description = "Operations related to Food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    //Post the food detail by Donor
    @PostMapping("/add")
    public ResponseEntity<Food> saveFoodDetail(@RequestBody Food food) {
        Food saveFood = foodService.saveFoodDetail(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveFood);
    }

    // existing - get food by foodId
    @GetMapping("/get/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        return ResponseEntity.ok(food);
    }

    // new - get all food by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Food>> getFoodByUserId(@PathVariable Long userId) {
        List<Food> foods = foodService.getFoodByUserId(userId);
        return ResponseEntity.ok(foods);
    }

    //Delete the food post by Donor
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFoodById(@PathVariable Long id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.noContent().build();
    }

    //Update the food post by Donor
    @PutMapping("/update/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food food) {
        Food updatedFood = foodService.updateFoodList(id, food);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFood);
    }

    @GetMapping("/discover")
    public ResponseEntity<?> discover(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5") double radius
    ) {
        return ResponseEntity.ok(
                foodService.discoverNearbyFood(lat, lng, radius)
        );
    }


    @GetMapping("/smart-search")
    public ResponseEntity<?> smartSearch(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String foodType,
            @RequestParam(required = false) Boolean freeOnly,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Double radius
    ) {
        return ResponseEntity.ok(
                foodService.smartSearch(
                        location, foodType, freeOnly, lat, lng, radius
                )
        );
    }

}
