package com.Food_Saver.demo.repository;

import com.Food_Saver.demo.entity.Chat;
import com.Food_Saver.demo.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {

    List<Chat> findBySenderEmailAndReceiverEmailOrSenderEmailAndReceiverEmail(String sender, String receiver,
            String receiver1, String sender1);

    @Query("""
                SELECT c FROM Chat c
                WHERE (c.senderEmail = :sender AND c.receiverEmail = :receiver)
                   OR (c.senderEmail = :receiver AND c.receiverEmail = :sender)
                ORDER BY c.timestamp ASC
            """)
    List<Chat> getChatHistory(
            @Param("sender") String sender,
            @Param("receiver") String receiver);

    List<Chat> findBySenderEmailOrReceiverEmail(
            String senderEmail,
            String receiverEmail);

    @Query("""
                SELECT c FROM Chat c
                WHERE c.senderEmail = :email OR c.receiverEmail = :email
                ORDER BY c.timestamp DESC
            """)
    List<Chat> findAllUserChats(@Param("email") String email);

    @Query("""
                SELECT c FROM Chat c
                WHERE c.conversation = :conversation
                ORDER BY c.timestamp ASC
            """)
    List<Chat> findByConversation(@Param("conversation") Conversation conversation);

}
