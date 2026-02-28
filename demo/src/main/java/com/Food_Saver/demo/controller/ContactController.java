package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.dto.ContactRequestDto;
import com.Food_Saver.demo.entity.Contact;
import com.Food_Saver.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/send/{foodId}/{receiverId}")
    public ResponseEntity<?> sendRequest(
            @PathVariable Long foodId,
            @PathVariable Long receiverId,
            @RequestBody ContactRequestDto requestDto) {
        Contact postContact = contactService.sendRequest(foodId,requestDto.getMessage(),receiverId);
        return ResponseEntity.ok(postContact);
    }

    @GetMapping("/myRequest/{requestId}")
    public ResponseEntity<?> getMyRequest(
            @PathVariable Long requestId){
        Contact foodRequest = contactService.getMyFoodRequest(requestId);
        return ResponseEntity.ok(foodRequest);
    }

    @PutMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptRequest(
            @PathVariable Long requestId){
        Contact accept = contactService.requestAccepted(requestId);
        return ResponseEntity.ok(accept);
    }

    @PutMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectRequest(
            @PathVariable Long requestId){
        Contact reject = contactService.rejectRequest(requestId);
        return ResponseEntity.ok(reject);
    }

}
