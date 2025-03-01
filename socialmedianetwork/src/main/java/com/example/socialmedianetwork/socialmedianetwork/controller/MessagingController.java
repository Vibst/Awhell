package com.example.socialmedianetwork.socialmedianetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socialmedianetwork.socialmedianetwork.entity.Message;
import com.example.socialmedianetwork.socialmedianetwork.services.MessageProducerService;

@RestController
@RequestMapping("/api")
public class MessagingController {

    private final MessageProducerService kafkaProducerService;

    public MessagingController(MessageProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping(value = "/produce")
    public ResponseEntity<Boolean> produce(@RequestBody Message myMsg) {
        try {
            kafkaProducerService.sendMessage("SocialNetwork", myMsg);
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (Exception ex) {
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }
}

