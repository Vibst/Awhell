package com.socilanetworking.postservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.socilanetworking.postservice.entity.Post;

import com.socilanetworking.postservice.model.PostModel;
import com.socilanetworking.postservice.service.Postservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/posts")
public class PostController {

    private static final Logger logger = LogManager.getLogger(PostController.class);

    @Autowired
    private Postservice postService;

    @PostMapping("/createPost")
    public ResponseEntity<PostModel> createPost(@RequestBody Post post) {
        if (post.getUserPostId() == null && post.equals(null)) {
            logger.warn("User is not associated with the post!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            PostModel model = postService.createPost(post);
            logger.info("Post created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(model);
        } catch (Exception e) {
            logger.error("Error in createPost: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostModel> getSpecificPost(@PathVariable Long postId) {
        try {
            PostModel returnPost = postService.findByPostId(postId);
            if (returnPost == null) {
                logger.warn("No post found with ID: {}", postId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(returnPost);
        } catch (Exception e) {
            logger.error("Error in getSpecificPost: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/getAllPost")
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> allPosts = postService.getAllPost();
            if (allPosts.isEmpty()) {
                logger.info("No posts available.");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(allPosts);
        } catch (Exception e) {
            logger.error("Error in getAllPosts: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<PostModel> deleteSpecificPost(@PathVariable Long postId) {
        try {
            PostModel deletedPost = postService.deletespecificPost(postId);
            if (deletedPost == null) {
                logger.warn("No post found to delete with ID: {}", postId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(deletedPost);
        } catch (Exception e) {
            logger.error("Error in deleteSpecificPost: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteAllPosts() {
        try {
            String result = postService.deleteAllPost();
            if (result == null || result.isEmpty()) {
                logger.warn("No posts found to delete.");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error in deleteAllPosts: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // please create a Api update Post And how can get validate the user those are
    // post the content and please createa an Api with extra and using AI/ ML Algo

}
