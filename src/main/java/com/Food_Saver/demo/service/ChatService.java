package com.Food_Saver.demo.service;

import com.Food_Saver.demo.dto.ConversationDTO;
import com.Food_Saver.demo.entity.Chat;
import com.Food_Saver.demo.entity.Conversation;
import com.Food_Saver.demo.entity.Food;
import com.Food_Saver.demo.entity.User;
import com.Food_Saver.demo.repository.ChatRepo;
import com.Food_Saver.demo.repository.ConversationRepo;
import com.Food_Saver.demo.repository.FoodRepo;
import com.Food_Saver.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepo chatRepo;
    private final UserRepo userRepo;
    private final FoodRepo foodRepo;
    private final ConversationRepo conversationRepo;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Chat sendMessage(Long foodPostId, String message) {
        User currentUser = getCurrentUser();

        Food food = foodRepo.findById(foodPostId)
                .orElseThrow(() -> new RuntimeException("Food Not found"));

        // The current user is the SENDER (whoever hits the API)
        String senderEmail = currentUser.getEmail();
        String donorEmail = food.getUser().getEmail();

        // Determine who the receiver of this message is
        String receiverEmail;
        boolean senderIsDonor = donorEmail.equals(senderEmail);

        String foodPostStr = String.valueOf(foodPostId);

        if (senderIsDonor) {
            // Bug 4 fix: Donor is replying — find existing conversation for this food post
            Optional<Conversation> existing = conversationRepo.findByDonorEmailAndFoodPost(donorEmail, foodPostStr);
            if (existing.isPresent()) {
                Conversation conversation = existing.get();
                receiverEmail = conversation.getReceiverEmail();

                // Save chat message
                Chat chat = new Chat();
                chat.setSenderEmail(senderEmail);
                chat.setReceiverEmail(receiverEmail);
                chat.setContent(message);
                chat.setConversation(conversation);
                return chatRepo.save(chat);
            } else {
                throw new RuntimeException("No conversation found. A receiver must initiate the conversation first.");
            }
        } else {
            // Receiver is sending a message to the donor
            receiverEmail = donorEmail;

            // Look up or create conversation
            Optional<Conversation> existing = conversationRepo.findConversation(senderEmail, donorEmail, foodPostStr);
            Conversation conversation;

            if (existing.isPresent()) {
                conversation = existing.get();
            } else {
                // Receiver initiates a new conversation
                conversation = new Conversation();
                conversation.setDonorEmail(donorEmail);
                conversation.setReceiverEmail(senderEmail); // the person initiating is the receiver
                conversation.setFoodPost(foodPostStr);
                conversation = conversationRepo.save(conversation);
            }

            // Save chat message
            Chat chat = new Chat();
            chat.setSenderEmail(senderEmail);
            chat.setReceiverEmail(receiverEmail);
            chat.setContent(message);
            chat.setConversation(conversation);
            return chatRepo.save(chat);
        }
    }

    public Conversation findOrCreateConversation(String senderEmail, String receiverEmail, String foodPostId) {
        Optional<Conversation> existing = conversationRepo.findConversation(senderEmail, receiverEmail, foodPostId);

        if (existing.isPresent()) {
            return existing.get();
        }

        // Bug 1 fix: Determine actual donor from the food post
        String actualDonorEmail = receiverEmail; // default fallback
        String actualReceiverEmail = senderEmail;

        try {
            Food food = foodRepo.findById(Long.parseLong(foodPostId)).orElse(null);
            if (food != null) {
                actualDonorEmail = food.getUser().getEmail();
                actualReceiverEmail = actualDonorEmail.equals(senderEmail) ? receiverEmail : senderEmail;
            }
        } catch (NumberFormatException ignored) {
            // If foodPostId is not a valid number, fall back to default
        }

        Conversation conversation = new Conversation();
        conversation.setDonorEmail(actualDonorEmail);
        conversation.setReceiverEmail(actualReceiverEmail);
        conversation.setFoodPost(foodPostId);
        return conversationRepo.save(conversation);
    }

    public List<ConversationDTO> getConversations(String email) {

        // Get all conversations where this user is involved
        List<Conversation> conversations = conversationRepo.findByDonorEmailOrReceiverEmail(email, email);

        List<ConversationDTO> result = new ArrayList<>();

        for (Conversation conv : conversations) {
            // Determine the partner email
            String partnerEmail = conv.getDonorEmail().equals(email)
                    ? conv.getReceiverEmail()
                    : conv.getDonorEmail();

            // Look up partner name
            String partnerName = userRepo.findByEmail(partnerEmail)
                    .map(User::getName)
                    .orElse(partnerEmail);

            // Look up food title
            String foodTitle = null;
            String foodPostId = conv.getFoodPost();
            if (foodPostId != null) {
                try {
                    foodTitle = foodRepo.findById(Long.parseLong(foodPostId))
                            .map(Food::getTitle)
                            .orElse(null);
                } catch (NumberFormatException ignored) {
                    // invalid food post id, skip
                }
            }

            // Get the latest chat message for this conversation
            List<Chat> chats = chatRepo.findByConversation(conv);
            String lastMessage = null;
            java.time.LocalDateTime timestamp = conv.getTimeStamp();
            if (!chats.isEmpty()) {
                Chat lastChat = chats.get(chats.size() - 1);
                lastMessage = lastChat.getContent();
                timestamp = lastChat.getTimestamp();
            }

            ConversationDTO dto = new ConversationDTO();
            dto.setConversationId(conv.getConversationId());
            dto.setPartnerEmail(partnerEmail);
            dto.setLastMessage(lastMessage);
            dto.setTimestamp(timestamp);
            dto.setPartnerName(partnerName);
            dto.setFoodTitle(foodTitle);
            dto.setFoodPostId(foodPostId);

            result.add(dto);
        }

        return result;
    }

    public Chat saveMessage(Chat message) {
        return chatRepo.save(message);
    }

    public void deleteConversation(Long convId) {
        conversationRepo.deleteById(convId);
    }


}
