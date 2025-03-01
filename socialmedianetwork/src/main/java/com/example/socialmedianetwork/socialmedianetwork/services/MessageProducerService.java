package com.example.socialmedianetwork.socialmedianetwork.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.socialmedianetwork.socialmedianetwork.entity.Message;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageProducerService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public MessageProducerService(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, Message values) {
        try {
            var future = kafkaTemplate.send(topicName, values);

            future.whenComplete((sendResult, exception) -> {
                if (exception != null) {
                    log.error("Failed to send message to Kafka", exception);
                } else {
                    log.info("Task status sent to Kafka topic: {}, Object: {}, result Set IS: {} ", topicName, values,
                            sendResult);
                }
            });

        } catch (Exception e) {
            log.error("Error sending message to Kafka", e);
        }
    }

    @KafkaListener(topics = "SocialNetwork", groupId = "SocialNetworkId")
    public void recieveMessageInKafka(String msg) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Try parsing as JSON
            if (msg.startsWith("{")) {
                Message message = objectMapper.readValue(msg, Message.class);
                log.info("Received JSON message: {}", message);
            } else {
                log.info("Received plain text message: {}", msg);
            }

        } catch (JsonMappingException e) {
            log.error("Invalid JSON format: {}", msg);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }

    }
}
