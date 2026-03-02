package com.Food_Saver.demo.repository;

import com.Food_Saver.demo.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<ChatMessage ,Long> {

    @Query("""
        SELECT c FROM ChatMessage c
        WHERE (c.senderEmail = :user1 AND c.receiverEmail = :user2)
        OR (c.senderEmail = :user2 AND c.receiverEmail = :user1)
        ORDER BY c.timestamp ASC
    """)
    List<ChatMessage> getChatHistory(String user1, String user2);

    List<ChatMessage> findBySenderEmailAndReceiverEmailOrSenderEmailAndReceiverEmail(
            String sender1, String receiver1, String sender2, String receiver2
    );


}
