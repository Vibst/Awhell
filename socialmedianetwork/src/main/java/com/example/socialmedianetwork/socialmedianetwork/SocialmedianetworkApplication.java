package com.example.socialmedianetwork.socialmedianetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.socialmedianetwork.socialmedianetwork.entity.Message;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SocialmedianetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialmedianetworkApplication.class, args);
	}
	
	@KafkaListener(topics = "SocialNetwork", groupId = "SocialNetworkId")
    public void recieveMessageInKafka(String msg){
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
