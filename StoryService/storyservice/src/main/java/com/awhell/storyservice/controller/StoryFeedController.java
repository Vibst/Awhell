package com.awhell.storyservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.awhell.storyservice.model.StoryFeedModel;
import com.awhell.storyservice.service.StoryFeedService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/v2/storyfeed")
public class StoryFeedController {

    private final StoryFeedService storyFeedService;

    public StoryFeedController(StoryFeedService storyFeedService) {
        this.storyFeedService = storyFeedService;
    }

    @PostMapping("/createStory")
    @CircuitBreaker(name = "storyServiceCircuitBreaker", fallbackMethod = "fallbackCreateStoryAsync")
    @TimeLimiter(name = "storyServiceTimeLimiter", fallbackMethod = "fallbackCreateStoryAsync")
    @Retry(name = "storyServiceRetry", fallbackMethod = "fallbackCreateStoryAsync")
    @RateLimiter(name = "storyServiceRateLimiter", fallbackMethod = "fallbackCreateStoryAsync")
    @Bulkhead(name = "storyServiceBulkhead", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackCreateStoryAsync")
    public CompletableFuture<ResponseEntity<StoryFeedModel>> createStoryFeed(
            @RequestBody StoryFeedModel storyFeedRequest) {

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

            CompletableFuture<StoryFeedModel> modelData = this.storyFeedService.getAsyncData();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelData);
        } catch (Exception e) {
            System.out.println("Error Are Found  When execute" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

    @PostMapping("/async-data-combine")
    public ResponseEntity<CompletableFuture<StoryFeedModel>> getDataAsyncCombine() {
        try {

            CompletableFuture<StoryFeedModel> modelData = this.storyFeedService.getAsyncDataCombine();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(modelData);
        } catch (Exception e) {
            System.out.println("Error Are Found  When execute" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }
}
