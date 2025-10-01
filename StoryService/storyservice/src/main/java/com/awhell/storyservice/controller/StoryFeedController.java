package com.awhell.storyservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.awhell.storyservice.exception.CustomException;
import com.awhell.storyservice.model.StoryFeedModel;
import com.awhell.storyservice.service.StoryFeedService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.tracing.Tracer;
import lombok.val;

@RestController
@RequestMapping("/api/v2/storyfeed")
public class StoryFeedController {

    private final StoryFeedService storyFeedService;
    private final Tracer tracer;

    public StoryFeedController(StoryFeedService storyFeedService, Tracer tracer) {
        this.storyFeedService = storyFeedService;
        this.tracer = tracer;
    }

    @PostMapping("/createStory")
    @CircuitBreaker(name = "storyServiceCircuitBreaker", fallbackMethod = "fallbackCreateStoryAsync")
    @TimeLimiter(name = "storyServiceTimeLimiter", fallbackMethod = "fallbackCreateStoryAsync")
    @Retry(name = "storyServiceRetry", fallbackMethod = "fallbackCreateStoryAsync")
    @RateLimiter(name = "storyServiceRateLimiter", fallbackMethod = "fallbackCreateStoryAsync")
    @Bulkhead(name = "storyServiceBulkhead", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackCreateStoryAsync")
    public CompletableFuture<ResponseEntity<StoryFeedModel>> createStoryFeed(
            @RequestBody StoryFeedModel storyFeedRequest) {
        System.out.println("================= CONTROLLER TRIGGER =====================");

        return CompletableFuture.supplyAsync(() -> {
            StoryFeedModel model = storyFeedService.createStoryFeed(storyFeedRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(model);
        });
    }

    // ✅ Fallback method for async calls
    public CompletableFuture<ResponseEntity<StoryFeedModel>> fallbackCreateStoryAsync(
            StoryFeedModel request, Throwable ex) {

        StoryFeedModel fallback = new StoryFeedModel();
        fallback.setAuthor("Fallback User");
        fallback.setContent("Fallback triggered because service is unavailable: " + ex.getMessage());

        System.out.println("Fallback executed: " + ex.getMessage());

        return CompletableFuture.completedFuture(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallback));
    }

    @PostMapping("/async-data")
    public ResponseEntity<CompletableFuture<StoryFeedModel>> getDataAsync() {
        try {
            System.out.println("================= CONTROLLER TRIGGER =====================");

            CompletableFuture<StoryFeedModel> modelData = this.storyFeedService.getAsyncData();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelData);
        } catch (Exception e) {
            System.out.println("Error Are Found  When execute" + e.getMessage());
            throw new CustomException(HttpStatus.BAD_REQUEST, "This is a custom error! async-data");

        }

    }

    @PostMapping("/async-data-combine")
    public ResponseEntity<CompletableFuture<StoryFeedModel>> getDataAsyncCombine() {
        try {
            System.out.println("================= CONTROLLER TRIGGER =====================");

            CompletableFuture<StoryFeedModel> modelData = this.storyFeedService.getAsyncDataCombine();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelData);
        } catch (Exception e) {
            System.out.println("Error Are Found  When execute" + e.getMessage());
            throw new CustomException(HttpStatus.BAD_REQUEST, "This is a custom error! async-data-combine");

        }

    }

    @PostMapping("/sync-trace-id")
    public ResponseEntity<String> getDataSyncTraceId() {
        try {
            System.out.println("================= CONTROLLER TRIGGER =====================");

            io.micrometer.tracing.Span currentTracer = tracer.currentSpan();
            String traceId = currentTracer != null ? currentTracer.context().traceId() : "no-trace-id";
            String spanId = currentTracer != null ? currentTracer.context().spanId() : "no-span-id";

            System.out.println("Trace ID: " + traceId + ", Span ID: " + spanId);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("it is Distributed Tracing");
        } catch (Exception e) {
            System.out.println("Error Are Found  When execute" + e.getMessage());

            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            throw new CustomException(HttpStatus.BAD_REQUEST, "This is a custom error! sync-trace-id");

        }

    }

    @PostMapping("/custom")
    public ResponseEntity<String> customError() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "This is a custom error!");
    }

    @PostMapping("/runtime")
    public ResponseEntity<String> runtimeError() {
        throw new RuntimeException("Some unexpected error happened");
    }

    @PostMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("All good ✅");
    }

    // NOTE =========== traceId and Span id is not working in completableFutre
    // because completebale future is Asynchronous and this is use only synchronous

}
