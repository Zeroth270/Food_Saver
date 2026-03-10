package com.Food_Saver.demo.controller;

import com.Food_Saver.demo.dto.ConversationDTO;
import com.Food_Saver.demo.entity.Chat;
import com.Food_Saver.demo.repository.ChatRepo;
import com.Food_Saver.demo.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "Chat APIs", description = "Chat Between Donor And Receiver")
public class ChatController {

        private final ChatRepo chatRepo;
        private final ChatService chatService;

        // NOTE: Bugs 2 & 3 (auth bypass) cannot be fully fixed until proper JWT
        // authentication is configured in SecurityConfig.java (currently permitAll).
        // Once JWT auth is enabled, replace @RequestParam email with
        // SecurityContextHolder.getContext().getAuthentication().getName()

        @GetMapping("/history")
        public List<Chat> getHistory(
                        @RequestParam String sender,
                        @RequestParam String receiver) {
                return chatRepo.getChatHistory(sender, receiver);
        }

        @PostMapping("/send/{foodPost}")
        public ResponseEntity<?> sendMessage(
                        @PathVariable Long foodPost,
                        @RequestBody Map<String, String> body) {

                String message = body.get("message");

                return ResponseEntity.ok(chatService.sendMessage(foodPost, message));
        }

        @GetMapping("/conversations")
        public ResponseEntity<List<ConversationDTO>> getConversations(
                        @RequestParam String email) {
                return ResponseEntity.ok(
                                chatService.getConversations(email));
        }

}
