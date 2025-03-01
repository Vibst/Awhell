package com.example.socialmedianetwork.socialmedianetwork.models;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

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

public class PostRequest implements Serializable {
    private Long Id;
    private String textContent;
    private MultipartFile image;
    private MultipartFile video;
    private MultipartFile audio;
    private MultipartFile file;
    private String jsonData;
}
