package com.Food_Saver.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    // Broadcast to all users
    public void sendGlobalNotification(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }

    // Send to specific user
    public void sendPrivateNotification(String userEmail, String message) {
        messagingTemplate.convertAndSendToUser(
                userEmail,
                "/queue/notifications",
                message
        );
    }

}
