package com.Food_Saver.demo.repository;

import com.Food_Saver.demo.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepo extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.foodPostId.foodId = :foodId")
    List<Contact> findByFoodId(@Param("foodId") Long foodId);

}
