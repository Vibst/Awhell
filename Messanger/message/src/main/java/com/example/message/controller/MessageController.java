package com.example.message.controller;

import java.sql.Time;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.message.models.ChatMessage;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public String sendMessage(ChatMessage chatMessage) {
        System.out.println("Received message from " + chatMessage.getSender() + " to " + chatMessage.getReceiver()
                + ": " + chatMessage.getContent());

        chatMessage.setLocalDate(LocalDateTime.now());
        ChatMessage sendMessage = getContent(chatMessage);
        messagingTemplate.convertAndSend("/topic/message" + sendMessage.getReceiver(), sendMessage);
        System.out.println("-----------------" + sendMessage.getLocalDate());

        return "Successfully Send Message" + " " + sendMessage.getLocalDate();

    }

    public ChatMessage getContent(@RequestBody ChatMessage message) {
        try {

            // DB logics and Busness Logics Implements
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Are found In" + e.getMessage());
        }
        return message;

    }

}
