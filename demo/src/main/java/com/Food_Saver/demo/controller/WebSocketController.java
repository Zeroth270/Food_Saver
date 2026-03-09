package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.entity.Chat;
import com.Food_Saver.demo.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Tag(name = "WebSocket APIs", description = "Operations related Chat")
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/send")
    public void sendMessage(Chat message,
            @org.springframework.messaging.handler.annotation.Header(value = "foodPostId", required = false) String foodPostId) {

        // Associate message with a conversation if foodPostId is available
        if (foodPostId != null && message.getConversation() == null) {
            com.Food_Saver.demo.entity.Conversation conversation = chatService.findOrCreateConversation(
                    message.getSenderEmail(),
                    message.getReceiverEmail(),
                    foodPostId);
            message.setConversation(conversation);
        }

        // 1️⃣ Save in DB
        Chat savedMessage = chatService.saveMessage(message);

        // 2️⃣ Send to receiver
        messagingTemplate.convertAndSendToUser(
                savedMessage.getReceiverEmail(),
                "/queue/messages",
                savedMessage);

        // 3️⃣ Send back to sender
        messagingTemplate.convertAndSendToUser(
                savedMessage.getSenderEmail(),
                "/queue/messages",
                savedMessage);
    }

}
