package com.example.likecomment.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.likecomment.entity.CommentPost;
import com.example.likecomment.entity.LikePost;
import com.example.likecomment.model.CommentPostModel;
import com.example.likecomment.model.LikePostModel;
import com.example.likecomment.model.usersModel;
import com.example.likecomment.repository.CommentRepository;
import com.example.likecomment.repository.LikeAndCommentRepository;

import ch.qos.logback.core.util.StringUtil;

@Service
public class LikeAndCommentService {

    private static final Logger logger = LogManager.getLogger(LikeAndCommentService.class);
    private final Map<Integer, Integer> countLikes = new HashMap<>();

    @Autowired
    private LikeAndCommentRepository repositoryService;

    @Autowired
    private CommentRepository commentServiceRepo;

    @Autowired
    private RestTemplate restTemplate;

    public LikePostModel createLike(LikePost like) {
        LikePostModel returnValue = new LikePostModel();

        try {
            Integer userId = like.getUserLikeId();
            boolean isLike = like.isLike();
            String apiUrl = "http://localhost:9099/api/v2/users/getUsersById/" + userId;

            ResponseEntity<usersModel> model = restTemplate.getForEntity(apiUrl, usersModel.class);
            usersModel returnUsers = model.getBody();

            if (returnUsers != null) {

                if (isLike && Boolean.FALSE.equals(like.isPriviousLike())) {
                    if (countLikes.containsKey(userId)) {
                        logger.info("User {} already liked this post", userId);
                        throw new IllegalStateException("User already liked the post");
                    } else {
                        if (returnUsers.equals(userId)) {
                            countLikes.put(userId, returnUsers.getUserId());
                            LikePost post = new LikePost();
                            post.setActive(true);
                            post.setLike(true);
                            post.setLikeUserActive(true);
                            post.setUserLikeId(userId);
                            post.setCountLike(like.getCountLike() + 1); // increment count

                            LikePost savedPost = repositoryService.save(post);
                            // BeanUtils.copyProperties(savedPost, returnValue);
                            returnValue.setActive(savedPost.isActive());
                            returnValue.setCountLike(savedPost.getCountLike());

                        }
                        return returnValue;

                    }
                } else if (Boolean.TRUE.equals(like.isPriviousLike())) {

                    countLikes.remove(userId);
                    LikePost post = new LikePost();
                    post.setActive(true);
                    post.setLike(false);
                    post.setLikeUserActive(false);
                    post.setUserLikeId(userId);
                    post.setCountLike(like.getCountLike() - 1); // decrement count

                    LikePost savedPost = repositoryService.save(post);
                    // BeanUtils.copyProperties(savedPost, returnValue);
                    returnValue.setActive(savedPost.isActive());
                    returnValue.setCountLike(savedPost.getCountLike());
                    returnValue.setLike(savedPost.isLike());
                    returnValue.setLikeUserActive(savedPost.isLikeUserActive());

                }

            } else {
                System.out.println("User Is Not Exit In The DB .." + returnUsers.getUserId());

            }

            return returnValue;

        } catch (Exception e) {
            logger.error("Error in createLike(): {}", e.getMessage());
            return null;
        }
    }

    public LikePostModel findByLikeId(Long likeId) {
        LikePostModel result = new LikePostModel();
        try {

            Optional<LikePost> likePost = this.repositoryService.findById(likeId);
            if (likePost.isPresent()) {
                // LikePost result =new LikePost();
                result.setActive(likePost.get().isActive());
                result.setCountLike(likePost.get().getCountLike());
                result.setLike(likePost.get().isLike());
                result.setLikeId(likePost.get().getLikeId());
                result.setLikeUserActive(likePost.get().isLikeUserActive());
                result.setUserLike(likePost.get().getUserLike());
                result.setUserLikeId(likePost.get().getUserLikeId());

                return result;

            }

        } catch (Exception e) {
            logger.info("something went wrong in find Like Id: {}", e.getMessage());

        }
        return result;
    }

    public List<LikePostModel> getAllLike() {
        List<LikePostModel> result = new ArrayList<>();

        try {
            List<LikePost> dbValue = (List<LikePost>) this.repositoryService.findAll();
            for (LikePost pst : dbValue) {
                LikePostModel likePost = new LikePostModel();
                likePost.setActive(pst.isActive());
                likePost.setCountLike(pst.getCountLike());
                likePost.setLike(pst.isLike());
                likePost.setLikeId(pst.getLikeId());
                likePost.setLikeUserActive(pst.isLikeUserActive());
                likePost.setUserLike(pst.getUserLike());
                likePost.setUserLikeId(pst.getUserLikeId());

                result.add(likePost);
            }

        } catch (Exception e) {
            logger.info("Error are Found In Get All Like Data : {}", e.getMessage());

        }
        return result;
    }

    public LikePostModel deletespecificLike(Long likeId) {
        LikePostModel returnValue = new LikePostModel();
        try {
            Optional<LikePost> dbValue = this.repositoryService.findById(likeId);
            if (dbValue.isPresent()) {
                returnValue.setActive(dbValue.get().isActive());
                returnValue.setCountLike(dbValue.get().getCountLike());
                returnValue.setLike(dbValue.get().isLike());
                returnValue.setLikeId(dbValue.get().getLikeId());
                returnValue.setLikeUserActive(dbValue.get().isLikeUserActive());
                returnValue.setUserLike(dbValue.get().getUserLike());
                returnValue.setUserLikeId(dbValue.get().getUserLikeId());

                return returnValue;

            }

        } catch (Exception e) {
            logger.info("Error are Found in Like Id: {} ", e.getMessage());

        }
        return returnValue;
    }

    public String deleteAllLikes() {
        try {
            this.repositoryService.deleteAll();
            logger.info("Successfully Deleted All Likes");
            return "Successfully Deleted All Likes";

        } catch (Exception e) {
            logger.info("Some Error are Found: {}", e.getMessage());
            return e.getMessage();

        }

    }

    // ------------------------------COMMENT
    // SERVICE------------------------------------------

    public CommentPostModel createComment(CommentPost comment) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            CommentPostModel modelValue = new CommentPostModel();
            modelValue.setCommentCreatedBy("admin");
            modelValue.setActiveComment(true);
            modelValue.setCommentMsg(comment.getCommentMsg());
            modelValue.setCommentTime(timeStamp);
            modelValue.setCommentedUserId(comment.getCommentedUserId());

            return modelValue;

        } catch (Exception e) {
            logger.info("Error are foound in Service: {}", e.getMessage());
            return null;

        }
    }

    public List<CommentPostModel> getAllComment() {
        List<CommentPostModel> result = new ArrayList<>();

        try {
            List<CommentPost> lstComment = (List<CommentPost>) this.commentServiceRepo.findAll();

            for (CommentPost lst : lstComment) {
                CommentPostModel cpm = new CommentPostModel();

                cpm.setActiveComment(lst.isActiveComment());
                cpm.setCommentCreatedBy(lst.getCommentCreatedBy());
                cpm.setCommentId(lst.getCommentId());
                cpm.setCommentMsg(lst.getCommentMsg());
                cpm.setCommentTime(lst.getCommentTime());
                cpm.setCommentedUser(lst.getCommentedUser());
                cpm.setCommentedUserId(lst.getCommentedUserId());

                result.add(cpm);
            }

        } catch (Exception e) {

            logger.info("Error retrieving comments: {}", e.getMessage());

        }

        return result;
    }

}
