package com.Food_Saver.demo.service;

import com.Food_Saver.demo.entity.Contact;
import com.Food_Saver.demo.entity.ContactStatus;
import com.Food_Saver.demo.entity.Food;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.repository.ContactRepo;
import com.Food_Saver.demo.repository.FoodRepo;
import com.Food_Saver.demo.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final FoodRepo foodRepo;

    private final UserRepo userRepo;

    private final ContactRepo contactRepo;

    private final SimpMessagingTemplate messageTemplate;

    public Contact sendRequest(Long foodId, String message, Long receiverId) {

        User receiver = userRepo.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food Not Found!"));

        Contact request = new Contact();
        request.setMessage(message);
        request.setFood(food);
        request.setReceiver(receiver);
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(ContactStatus.PENDING);

        return contactRepo.save(request);
    }

    public Contact getMyFoodRequest(Long requestId) {
        return contactRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Food Request Not Found!!"));
    }

    @Transactional
    public Contact requestAccepted(Long requestId) {
        Contact req = contactRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Not Found"));

        req.setStatus(ContactStatus.ACCEPT);
        Contact saveReq =  contactRepo.save(req);

        String receiverEmail = saveReq.getReceiver().getEmail();

        String foodTitle = saveReq.getFood().getTitle();

        if (receiverEmail != null && foodTitle != null) {
            messageTemplate.convertAndSendToUser(receiverEmail, "/queue/notifications",
                    "Your request for '" + foodTitle + "' is accepted!");
        }
        return saveReq;
    }

    @Transactional
    public Contact requestReject(Long requestId) {
        Contact req = contactRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Id Not Found!!"));

        req.setStatus(ContactStatus.REJECT);

        Contact saveReq = contactRepo.save(req);

        String receiverEmail = saveReq.getReceiver().getEmail();

        String foodTitle = saveReq.getFood().getTitle();

        if (receiverEmail != null && foodTitle != null) {
            messageTemplate.convertAndSendToUser(receiverEmail, "/queue/notifications",
                    "Your request for '" + foodTitle + "' has been rejected.");
        }

        return saveReq;
    }

    public List<Contact> getRequestsByFoodId(Long foodId) {
        return contactRepo.findByFoodId(foodId);
    }

}
