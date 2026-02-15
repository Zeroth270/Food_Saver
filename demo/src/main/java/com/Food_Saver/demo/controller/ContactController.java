package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.entity.Contact;
import com.Food_Saver.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

//    @PostMapping("send/{foodPostId}")
//    public ResponseEntity<?> sendRequest(
//            @PathVariable Long foodId,
//            @RequestBody Map<String, String> body){
//        String message = body.get("message");
//        Contact postContact = contactService.sendRequest(foodId,message);
//        return ResponseEntity.ok(postContact);
//    }

}
