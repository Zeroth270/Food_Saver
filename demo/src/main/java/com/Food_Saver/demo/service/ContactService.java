package com.Food_Saver.demo.service;


import com.Food_Saver.demo.repository.ContactRepo;
import com.Food_Saver.demo.repository.FoodRepo;
import com.Food_Saver.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ContactRepo contactRepo;



}



