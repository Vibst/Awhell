package com.example.socialmedianetwork.socialmedianetwork.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import com.example.socialmedianetwork.socialmedianetwork.models.ConfigurationParametersModels;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class readConfiguration {

    @PostConstruct
    public ConfigurationParametersModels setConfiguration() throws IOException {
        File fileDir = new File("/home/coral/ScalaConfiguration");
        FileReader fr = new FileReader(fileDir);
        BufferedReader br = new BufferedReader(fr);
        ConfigurationParametersModels config = new ConfigurationParametersModels();
        String lines;

        while ((lines = br.readLine()) != null) {
            log.info(" lines of File is ; {}", lines);
            String[] parts = lines.split("=");

            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "KAFKA_IP" -> config.setKAFKA_IP(value);
                    case "KAFKA_PORT" -> config.setKAFKA_PORT(Integer.parseInt(value));
                    case "ACTIVE_PARAMETER" -> config.setACTIVE_PARAMETER(Boolean.parseBoolean(value));
                    case "ROUTING_IP" -> config.setROUTING_IP(value);
                    case "ROUTING_PORT" -> config.setROUTING_PORT(Integer.parseInt(value));
                    case "APPLICATION_ACTIVE" -> config.setAPPLICATION_ACTIVE(Boolean.parseBoolean(value));
                    case "PROMETHEUS_IP" -> config.setPROMETHEUS_IP(value);
                    case "PROMETHEUS_PORT" -> config.setPROMETHEUS_PORT(Integer.parseInt(value));
                    case "PUSHGATEWAY_IP" -> config.setPUSHGATEWAY_IP(value);
                    case "PUSHGATEWAY_PORT" -> config.setPUSHGATEWAY_PORT(Integer.parseInt(value));
                    case "APPLICATION_IP" -> config.setAPPLICATION_IP(value);
                    case "APPLICATION_PORT" -> config.setAPPLICATION_PORT(Integer.parseInt(value));
                    default -> log.warn("Unknown key: {}", key);
                }
            }

        }
        br.close();
        fr.close();

        return config;
    }
}
