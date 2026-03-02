package com.Food_Saver.demo.service;

import com.Food_Saver.demo.entity.Contact;
import com.Food_Saver.demo.entity.ContactStatus;
import com.Food_Saver.demo.entity.Food;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.repository.ContactRepo;
import com.Food_Saver.demo.repository.FoodRepo;
import com.Food_Saver.demo.repository.UserRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactService {

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ContactRepo contactRepo;

    public Contact sendRequest(Long foodId, String message, Long receiverId) {

        User receiver = userRepo.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food Not Found!"));

        Contact request = new Contact();
        request.setMessage(message);
        request.setFoodPostId(food);
        request.setReceiver(receiver);
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(ContactStatus.PENDING);

        return contactRepo.save(request);
    }

    public Contact getMyFoodRequest(Long requestId) {
        return contactRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Food Request Not Found!!"));
    }

    public Contact requestAccepted(Long requestId) {
        Contact req = contactRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Not Found"));

        req.setStatus(ContactStatus.ACCEPT);
        return contactRepo.save(req);
    }

    public Contact rejectRequest(Long requestId) {
        Contact req = contactRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Id Not Found!!"));

        req.setStatus(ContactStatus.REJECT);

        return contactRepo.save(req);
    }

}
