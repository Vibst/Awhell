package com.example.socialmedianetwork.socialmedianetwork.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class portConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Autowired
    private readConfiguration read;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        try {
           // ConfigurationParametersModels cpm = read.setConfiguration();
            factory.setPort(read.setConfiguration().getAPPLICATION_PORT());
            log.info("The Application Is Running The Port is: {}", read.setConfiguration().getAPPLICATION_PORT());

        } catch (Exception e) {
            log.info("Error are found in Set Port :{}", e.getMessage());

        }

    }

}
