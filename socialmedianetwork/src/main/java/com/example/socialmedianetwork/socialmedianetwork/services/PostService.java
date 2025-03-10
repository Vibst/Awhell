package com.example.socialmedianetwork.socialmedianetwork.services;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.socialmedianetwork.socialmedianetwork.entity.Message;
import com.example.socialmedianetwork.socialmedianetwork.entity.Post;
import com.example.socialmedianetwork.socialmedianetwork.models.PostRequest;
import com.example.socialmedianetwork.socialmedianetwork.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemp;

    private Optional<Post> deleteData;
    private Message message;
    Post savePost = null;

    public Post createPost(PostRequest postRequest) {
        try {

            Post post = new Post();
            post.setTextContent(postRequest.getTextContent());
            post.setJsonData(postRequest.getJsonData());

            if (postRequest.getImage() != null && !postRequest.getImage().isEmpty()) {
                post.setImageUrl(saveFile(postRequest.getImage(), "Image"));
            }

            if (postRequest.getAudio() != null && !postRequest.getAudio().isEmpty()) {
                post.setAudioUrl(saveFile(postRequest.getAudio(), "Audio"));
            }

            if (postRequest.getVideo() != null && !postRequest.getVideo().isEmpty()) {
                post.setVideoUrl(saveFile(postRequest.getVideo(), "Video"));
            }

            if (postRequest.getFile() != null && !postRequest.getFile().isEmpty()) {
                post.setFileUrl(saveFile(postRequest.getFile(), "Files"));
            }

          
            var future = kafkaTemp.send("SocialNetwork", "Successfully Create my Post in Post Service");

            future.whenComplete((sendResult, exception) -> {
                if (exception != null) {
                    log.error("Failed to send message to Kafka", exception);
                } else {
                    log.info("Task status sent to Kafka topic: {}, result Set IS: {} ", "SocialNetwork",sendResult);
                    savePost = postRepository.save(post);
                }
            });
            
            log.info("Successfully Send Notification in notification Services");
            return savePost;

        } catch (Exception e) {
            log.info("Errro are found in Service side: {}",e.getMessage());
            throw new UnsupportedOperationException("Unimplemented method 'createPost'"+ e.getMessage());
        }

    }

    public Post deletePost(PostRequest postRequest) {
        try {

            //////////////////////////////// Delete in Path of Directory
            //////////////////////////////// /////////////////////////////////

            if (postRequest.getAudio() != null && !postRequest.getAudio().isEmpty()) {
                String dlteFile = deleteFileFolder(postRequest.getFile(), "Audio");
                log.info("The Delete File Is Audio");

            } else {
                if (postRequest.getFile() != null && postRequest.getAudio().isEmpty()) {
                    String dlteFile = deleteFileFolder(postRequest.getFile(), "Files");
                    log.info("The Delete File Is  File ");
                } else {
                    if (postRequest.getImage() != null && !postRequest.getImage().isEmpty()) {
                        String dlteFile = deleteFileFolder(postRequest.getFile(), "Image");
                        log.info("The Delete File Is  Image ");
                    } else {
                        if (postRequest.getVideo() != null && postRequest.getVideo().isEmpty()) {
                            String dlteFile = deleteFileFolder(postRequest.getFile(), "Video");
                            log.info("The Delete File Is  Videos ");
                        } else {
                            log.info("No Any One Media is Found !!!");
                        }

                    }

                }

            }

            ///////////////////////// Delete in repository
            ///////////////////////// ///////////////////////////////////////////

            deleteData = postRepository.findById(postRequest.getId());
            if (deleteData.isPresent()) {
                postRepository.deleteById(deleteData.get().getId());
                log.info("The Delete Data is Successfully: {}");
            }
            return deleteData.get();

        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'deletePost'" + e.getMessage());
        }

    }

    public List<Post> listCreatePost() {
        try {
            List<Post> allPostList = postRepository.findAll();
            if (!allPostList.isEmpty() && allPostList.size() > 0) {

                for (Post listValue : allPostList) {
                    Post assignValue = new Post();
                    assignValue.setAudioUrl(listValue.getAudioUrl());
                    assignValue.setCreatedAt(listValue.getCreatedAt());
                    assignValue.setFileUrl(listValue.getFileUrl());
                    assignValue.setId(listValue.getId());
                    assignValue.setImageUrl(listValue.getImageUrl());
                    assignValue.setJsonData(listValue.getJsonData());
                    assignValue.setTextContent(listValue.getTextContent());
                    assignValue.setVideoUrl(listValue.getVideoUrl());

                    log.info("assignValue is {} = :", assignValue);

                }
                return allPostList;

            }
            return null;

        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listCreatePost'");
        }

    }

    public Post getSpecificPost(Long id) {
        try {
            Optional<Post> specificValue = postRepository.findById(id);
            if (specificValue.isPresent()) {

                Post returnValue = new Post();
                returnValue.setAudioUrl(specificValue.get().getAudioUrl());
                returnValue.setCreatedAt(specificValue.get().getCreatedAt());
                returnValue.setFileUrl(specificValue.get().getFileUrl());
                returnValue.setId(specificValue.get().getId());
                returnValue.setImageUrl(specificValue.get().getImageUrl());
                returnValue.setJsonData(specificValue.get().getJsonData());
                returnValue.setTextContent(specificValue.get().getTextContent());
                returnValue.setVideoUrl(specificValue.get().getVideoUrl());

                return returnValue;

            }
            return null;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'getSpecificPost'");
        }

    }

    private String saveFile(MultipartFile file, String folder) throws IOException {
        Path uploadDir = Paths.get(UPLOAD_DIR + folder);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return folder + "/" + fileName;

    }

    private String deleteFileFolder(MultipartFile file, String folder) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR + folder + "/" + file.getOriginalFilename());

        if (Files.exists(filePath)) {
            Files.delete(filePath);
            log.info("File {} deleted successfully from {}", file.getOriginalFilename(), folder);
        } else {
            log.warn("File {} not found in {}", file.getOriginalFilename(), folder);
        }
        return file.getOriginalFilename();
    }

}
