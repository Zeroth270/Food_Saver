package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.dto.ContactRequestDto;
import com.Food_Saver.demo.entity.Contact;
import com.Food_Saver.demo.service.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@Tag(name = "Contact APIs", description = "Contact With Each Other")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/send/{foodId}/{receiverId}")
    public ResponseEntity<?> sendRequest(
            @PathVariable Long foodId,
            @PathVariable Long receiverId,
            @RequestBody ContactRequestDto requestDto) {
        Contact postContact = contactService.sendRequest(foodId, requestDto.getMessage(), receiverId);
        return ResponseEntity.ok(postContact);
    }

    @GetMapping("/myRequest/{requestId}")
    public ResponseEntity<?> getMyRequest(
            @PathVariable Long requestId) {
        Contact foodRequest = contactService.getMyFoodRequest(requestId);
        return ResponseEntity.ok(foodRequest);
    }

    @PutMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptRequest(
            @PathVariable Long requestId) {
        Contact accept = contactService.requestAccepted(requestId);
        return ResponseEntity.ok(accept);
    }

    @PutMapping("/reject/{requestId}")
    public ResponseEntity<?> requestReject(
            @PathVariable Long requestId) {
        Contact reject = contactService.requestReject(requestId);
        return ResponseEntity.ok(reject);
    }

    @GetMapping("/food/{foodId}")
    public ResponseEntity<List<Contact>> getRequestsByFoodId(@PathVariable Long foodId) {
        List<Contact> requests = contactService.getRequestsByFoodId(foodId);
        return ResponseEntity.ok(requests);
    }

}
