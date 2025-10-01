package com.awhell.storyservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.awhell.storyservice.model.StoryFeedModel;
import com.awhell.storyservice.model.UsersModel;
import com.awhell.storyservice.scoping.StoryCreateTag;
import com.awhell.storyservice.scoping.UserCheckTage;

@Service
public class StoryFeedService {

    @Autowired(required = false)
    private UserCheckTage userCheckTage;

    @Autowired(required = false)
    private StoryCreateTag storycreateTag;

    private final RestTemplate restTemplate;

    public StoryFeedService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StoryFeedModel createStoryFeed(StoryFeedModel storyFeedRequest) {
        try {
            String apiUrl = "http://USERS/api/v2/users/getUsersById/" + storyFeedRequest.getUserId();

            ResponseEntity<UsersModel> response = restTemplate.exchange(apiUrl, HttpMethod.POST, null,
                    UsersModel.class);

            UsersModel usersModel = response.getBody();
            if (usersModel != null) {
                storyFeedRequest.setAuthor(usersModel.getUserName());
                storyFeedRequest.setUsersModel(usersModel);
            }

        } catch (Exception e) {
            // Exception will be handled by Resilience4j fallback
            throw new RuntimeException("USERS service call failed: " + e.getMessage());
        }

        return storyFeedRequest;
    }

    @Async
    public CompletableFuture<StoryFeedModel> getAsyncData() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                StoryFeedModel model = new StoryFeedModel();
                model.setAuthor("Async Author");
                model.setContent("This is async content fetched asynchronously.");
                return model;
            } catch (Exception e) {
                throw new RuntimeException("Error in async task", e);
            }
        }).thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            try {
                String apiUrl = "http://USERS/api/v2/users/listAll";

                // Example GET call for listAll users
                ResponseEntity<UsersModel[]> response = restTemplate.exchange(
                        apiUrl,
                        HttpMethod.POST,
                        null,
                        UsersModel[].class);

                UsersModel[] usersModels = response.getBody();
                System.out.println("Users Model in Async: " + (usersModels != null ? usersModels.length : 0));

                StoryFeedModel model = new StoryFeedModel();
                model.setAuthor("Async Author with Users");
                model.setContent("This is async content with users fetched asynchronously.");
                return model;
            } catch (Exception e) {
                throw new RuntimeException("Error fetching users asynchronously", e);
            }
        }));
    }

    public CompletableFuture<StoryFeedModel> getAsyncDataCombine() {
        CompletableFuture<StoryFeedModel> finalresult = null;
        try {
            CompletableFuture<StoryFeedModel> storyFeed = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                StoryFeedModel model = new StoryFeedModel();
                model.setAuthor("Combined Async Author");
                model.setContent("This is combined async content fetched asynchronously.");
                return model;
            });

            CompletableFuture<List<UsersModel>> userStoryFeed = CompletableFuture.supplyAsync(() -> {
                try {
                    String apiUrl = "http://USERS/api/v2/users/listAll";

                    // Example GET call for listAll users
                    ResponseEntity<UsersModel[]> response = restTemplate.exchange(
                            apiUrl,
                            HttpMethod.POST,
                            null,
                            UsersModel[].class);

                    UsersModel[] usersModels = response.getBody();
                    System.out
                            .println("Users Model in Async Combine: " + (usersModels != null ? usersModels.length : 0));
                    return List.of(usersModels != null ? usersModels : new UsersModel[0]);
                } catch (Exception e) {
                    throw new RuntimeException("Error fetching users asynchronously", e);
                }
            });

            finalresult = storyFeed.thenCombine(userStoryFeed, (story, users) -> {

                StoryFeedModel model = new StoryFeedModel();
                model.setAuthor("Combined Async Author with Users" + story.getAuthor());
                model.setContent(users.size() > 1 ? users.get(0).getUserName() : "No Users");
                System.out.println("Final Combined Model: " + model.getAuthor() + ", Users Count: " + users.size());

                return model;
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return finalresult;
    }
}
