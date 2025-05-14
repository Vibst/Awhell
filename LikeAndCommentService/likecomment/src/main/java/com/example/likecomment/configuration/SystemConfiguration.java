package com.example.likecomment.configuration;

import java.io.FileInputStream;
import java.io.InputStream;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;

@Configuration
// @Service
public class SystemConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SystemConfiguration.class);

    private String applicationIp;
    private String applicationPort;
    private String kafkaIp;
    private String kafkaPort;
    private String prometheusIp;
    private String prometheusPort;

    @Value("${app.config}")  
    private String configPath;

    @PostConstruct
    public String init() {
        readConfigFile();

        return "Initaialise The Project";

    }

    public void readConfigFile() {
        try {

            InputStream input =new FileInputStream(configPath);
            Ini ini = new Ini(input);
            java.util.prefs.Preferences configuration = new IniPreferences(ini);

            this.applicationIp = configuration.node("POST-SERVICE").get("APPLICATION-SERVER-IP", "127.0.0.1");
            this.applicationPort = configuration.node("POST-SERVICE").get("APPLICATION-SERVER-PORT", "9093");
            this.kafkaIp = configuration.node("POST-SERVICE").get("KAFKA-BROKER-IP", "127.0.0.1");
            this.kafkaPort = configuration.node("POST-SERVICE").get("KAFKA-BROKER-PORT", "9092");
            this.prometheusIp = configuration.node("POST-SERVICE").get("PROMETHEUS-IP", "127.0.0.1");
            this.prometheusPort = configuration.node("POST-SERVICE").get("PROMETHEUS-PORT", "9090");

            logger.info(
                    "the configuration values is applicationServer {}, Application Port {}, kafka ip {}, kafka port {},prometheus ip {}, prometheus port {}",
                    applicationIp, applicationPort, kafkaIp, kafkaPort, prometheusIp, prometheusPort);

        } catch (Exception e) {
            logger.info("Error In SystemConfiguration file {}", e.getMessage());

        }

    }

}
