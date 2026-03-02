package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.entity.ChatMessage;
import com.Food_Saver.demo.repository.ChatRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "Chat APIs", description = "Chat Between Donor And Receiver")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepo chatRepo;

    @MessageMapping("/send")
    public void sendMessage(ChatMessage message) {

        // 1️⃣ Save to DB
        chatRepo.save(message);

        // 2️⃣ Send to receiver
        messagingTemplate.convertAndSendToUser(
                message.getReceiverEmail(),
                "/queue/messages",
                message
        );
    }

    @GetMapping("/history")
    public List<ChatMessage> getHistory(@RequestParam String sender, @RequestParam String receiver) {
        return chatRepo.findBySenderEmailAndReceiverEmailOrSenderEmailAndReceiverEmail(
                sender, receiver, receiver, sender
        );
    }


}
