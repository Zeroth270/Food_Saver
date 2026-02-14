package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.entity.Food;
import com.Food_Saver.demo.service.FoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
@Tag(name = "Food APIs", description = "Operations related to Food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/add")
    public ResponseEntity<Food> saveFoodDetail(@RequestBody Food food){
         Food saveFood = foodService.saveFoodDetail(food);
         return ResponseEntity.status(HttpStatus.CREATED).body(saveFood);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Food> getFoodDetail(@PathVariable Long id){
        Food getFood = foodService.getFoodDetail(id);
        return ResponseEntity.ok(getFood);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFoodById(@PathVariable Long id){
        foodService.deleteFoodById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id,@RequestBody Food food){
        Food updatedFood = foodService.updateFoodList(id,food);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFood);
    }

}
