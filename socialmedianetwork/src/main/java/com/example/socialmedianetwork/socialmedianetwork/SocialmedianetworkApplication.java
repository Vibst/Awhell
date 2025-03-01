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
	
	
}
