package com.example.socialmedianetwork.socialmedianetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedianetwork.socialmedianetwork.entity.Post;
import com.example.socialmedianetwork.socialmedianetwork.models.PostRequest;
import com.example.socialmedianetwork.socialmedianetwork.services.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/sendPost")
    public ResponseEntity<Post> sendPost(@ModelAttribute PostRequest postRequest) {
        try {
            if (postRequest != null ) {
                Post createPost = postService.createPost(postRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(createPost);

            } else {
                log.info("Errror Are Found In User(RequestBody): {}", postRequest);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            }

        } catch (Exception e) {
            log.info("Something Went Wrong in Controller Side :{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        
    }

    @PostMapping("/deletePost/")
    public ResponseEntity<Post> deletePost(@ModelAttribute PostRequest postRequest) {
        try {
            if (postRequest != null) {
                Post deletePost = postService.deletePost(postRequest);

                return ResponseEntity.status(HttpStatus.CREATED).body(deletePost);
            } else {
                log.info("The User Is Not Select Any Image/File/Audio/Videos ");
            }

        } catch (Exception e) {
            log.info("Something Went Wrong in Controller Side :{}", e.getMessage());

        }
        return null;

    }

    @PostMapping("/listCreatePost")
    public ResponseEntity<List<Post>> listCreatePost() {
        try {
            List<Post> listCreatePost = postService.listCreatePost();

            return ResponseEntity.status(HttpStatus.CREATED).body(listCreatePost);

        } catch (Exception e) {
            log.info("Something Went Wrong in Controller Side :{}", e.getMessage());
        }
        return null;
    }

    @PostMapping("/specificGetPost/{id}")
    public ResponseEntity<Post> getSpecificPost(@PathVariable("id") Long id) {
        try {
            Post specificPost = postService.getSpecificPost(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(specificPost);

        } catch (Exception e) {
            log.info("Something Went Wrong in Controller Side :{}", e.getMessage());
        }
        return null;

    }

    @GetMapping("/upload")
    public String showUploadPage() {
        return "image_post"; // Thymeleaf template
    }
}
