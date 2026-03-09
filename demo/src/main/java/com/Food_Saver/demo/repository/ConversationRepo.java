package com.Food_Saver.demo.repository;

import com.Food_Saver.demo.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepo extends JpaRepository<Conversation, Long> {

    @Query("""
                SELECT c FROM Conversation c
                WHERE c.foodPost = :foodPost
                AND (
                    (c.donorEmail = :email1 AND c.receiverEmail = :email2)
                    OR
                    (c.donorEmail = :email2 AND c.receiverEmail = :email1)
                )
            """)
    Optional<Conversation> findConversation(
            @Param("email1") String email1,
            @Param("email2") String email2,
            @Param("foodPost") String foodPost);

    // Get all conversations for a user
    List<Conversation> findByDonorEmailOrReceiverEmail(
            String donorEmail,
            String receiverEmail);
}
