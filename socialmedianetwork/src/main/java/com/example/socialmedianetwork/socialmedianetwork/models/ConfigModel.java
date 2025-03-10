package com.example.socialmedianetwork.socialmedianetwork.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfigModel {
    private String KAFKA_IP;
    private Integer KAFKA_PORT; 
    private Boolean ACTIVE_PARAMETER;
    private String ROUTING_IP;
    private Integer ROUTING_PORT;
    private Boolean APPLICATION_ACTIVE;
    private String PROMETHEUS_IP;
    private Integer PROMETHEUS_PORT;
    private String PUSHGATEWAY_IP;
    private Integer PUSHGATEWAY_PORT;
    private String APPLICATION_IP;
    private Integer APPLICATION_PORT;
    
}
