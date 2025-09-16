package com.example.likecomment.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.likecomment.entity.CommentPost;
import com.example.likecomment.entity.LikePost;
import com.example.likecomment.model.CommentPostModel;
import com.example.likecomment.model.LikePostModel;
import com.example.likecomment.service.LikeAndCommentService;



@RestController
@RequestMapping("/api/v2/like")
@CrossOrigin(origins = "http://localhost:3000")
public class LikeAndCommentController {
    private static final Logger logger = LogManager.getLogger(LikeAndCommentController.class);

    @Autowired
    private LikeAndCommentService LikeService;

    @PostMapping(value = "/createLike", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompletableFuture<LikePostModel>> createLike(@RequestBody LikePost like) {
        if (like == null) {
            logger.warn("User is not associated with the post!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            CompletableFuture<LikePostModel> model = LikeService.createLike(like);
            logger.info("Post liked successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(model);
        } catch (IllegalStateException e) {
            logger.warn("Duplicate like attempt: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error("Error in createLike: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{likeId}")
    public ResponseEntity<LikePostModel> getSpecificLike(@PathVariable("likeId") Long likeId) {
        try {
            LikePostModel returnPost = LikeService.findByLikeId(likeId);
            if (returnPost == null) {
                logger.warn("No post found with ID: {}", likeId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(returnPost);
        } catch (Exception e) {
            logger.error("Error in getSpecificPost: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<List<LikePostModel>> getAllPosts() {
        try {
            System.out.println("Reached getAllPosts endpoint");
            List<LikePostModel> allPosts = LikeService.getAllLike();
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

    @PostMapping("/delete/{likeId}")
    public ResponseEntity<LikePostModel> deleteSpecificLike(@PathVariable Long likeId) {
        try {
            LikePostModel deletedPost = LikeService.deletespecificLike(likeId);
            if (deletedPost == null) {
                logger.warn("No post found to delete with ID: {}", likeId);
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
            String result = LikeService.deleteAllLikes();
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

    @PostMapping(value = "/createComment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentPostModel> createComment(@RequestBody CommentPost comment) {
        if (comment == null) {
            logger.warn("User is not associated with the post!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            CommentPostModel model = LikeService.createComment(comment);
            logger.info("Post liked successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(model);
        } catch (IllegalStateException e) {
            logger.warn("Duplicate like attempt: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            logger.error("Error in createLike: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/getAllComments")
    public ResponseEntity<List<CommentPostModel>> getAllComment() {
        try {
            List<CommentPostModel> allPosts = LikeService.getAllComment();
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

}
