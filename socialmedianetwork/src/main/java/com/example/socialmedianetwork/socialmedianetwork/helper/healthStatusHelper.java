package com.example.socialmedianetwork.socialmedianetwork.helper;

import java.io.IOException;
import java.security.Guard;

import org.hibernate.annotations.CollectionTypeRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class healthStatusHelper {

    public String statusCountInPostServices(String ServiceName, Long ServiceValue) throws IOException {
        CollectorRegistry registry = new CollectorRegistry();
        Gauge gauge = Gauge.build()
                .name(" Health In Post Services")
                .help("My Metric Post Service")
                .labelNames("status")
                .labelNames("os")
                .register(registry);

        Gauge createPost = Gauge.build().name(ServiceName).help("Count Create Post").register(registry);
        createPost.set(ServiceValue);

        PushGateway pushGateway = new PushGateway("192.168.8.85:9091");
        pushGateway.pushAdd(registry, "realDataAnalytics");

        log.info("Metric pushed successfully!");

        return "Sucessfully stand and Register";
    }


    public String healthInAllService(String ServiceName, Long ServiceValue) throws IOException {
        CollectorRegistry registry = new CollectorRegistry();
        Gauge gauge = Gauge.build()
                .name(" Health In Post Services")
                .help("My Metric Post Service")
                .labelNames("status")
                .labelNames("os")
                .register(registry);

        Gauge createPost = Gauge.build().name(ServiceName).help("Count Create Post").register(registry);
        createPost.set(ServiceValue);

        PushGateway pushGateway = new PushGateway("192.168.8.85:9091");
        pushGateway.pushAdd(registry, "pushgateway");

        log.info("Metric pushed successfully!");

        return "Sucessfully stand and Register";
    }

}
