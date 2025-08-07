package com.socilanetworking.postservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socilanetworking.postservice.controller.PostController;
import com.socilanetworking.postservice.entity.Post;

import com.socilanetworking.postservice.model.PostModel;
import com.socilanetworking.postservice.repository.PostRepository;
import org.springframework.beans.BeanUtils;

@Service
public class Postservice {

    private static final Logger logger = LogManager.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;


    private PostModel model;

    public PostModel createPost(Post postModel) {
        try {
            Post post = new Post();
            post.setCommentPostid(postModel.getCommentPostid());
            post.setCommentPosts(postModel.getCommentPosts());
            post.setContent(postModel.getContent());
            post.setImage(post.getImage());
            post.setLikePostId(postModel.getLikePostId());
            post.setLikePosts(postModel.getLikePosts());
            post.setPostId(postModel.getPostId());
            post.setPostTime(postModel.getPostTime());
            post.setRec(postModel.getRec());
            post.setSharePost(postModel.getSharePost());
            post.setUserPost(postModel.getUserPost());
            post.setUserPostId(postModel.getUserPostId());
            post.setVdo(postModel.getVdo());

            Post savedPost = postRepository.save(post);
            logger.info("Saved the post to DB.");

            PostModel model = new PostModel();
            BeanUtils.copyProperties(savedPost, model);
            return model;

        } catch (Exception e) {
            logger.error("Error in createPost: {}", e.getMessage(), e);
            return null;
        }
    }

    public PostModel findByPostId(Long postId) {
        try {
            Optional<Post> poId = postRepository.findById(postId);
            if (poId.isPresent()) {
                PostModel model = new PostModel(); // FIX
                BeanUtils.copyProperties(poId.get(), model);
                logger.info("Fetched specific post: {}", model);
                return model;
            } else {
                logger.warn("No post found with ID: {}", postId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error in findByPostId: {}", e.getMessage(), e);
            return null;
        }
    }

    public List<Post> getAllPost() {
        try {
            Post returnPost = new Post();
            List<Post> allPost = (List<Post>) postRepository.findAll(); // should never be null, but still safe to check

            for (Post ps : allPost) {
                returnPost.setCommentPostid(ps.getCommentPostid());
                returnPost.setCommentPosts(ps.getCommentPosts());
                returnPost.setContent(ps.getContent());
                returnPost.setImage(ps.getImage());
                returnPost.setLikePostId(ps.getLikePostId());
                returnPost.setLikePosts(ps.getLikePosts());
                returnPost.setPostId(ps.getPostId());
                returnPost.setPostTime(ps.getPostTime());
                returnPost.setRec(ps.getRec());
                returnPost.setSharePost(ps.getSharePost());
                returnPost.setUserPost(ps.getUserPost());
                returnPost.setUserPostId(ps.getUserPostId());
                returnPost.setVdo(ps.getVdo());

            }

            if (returnPost == null || allPost.isEmpty()) {
                logger.info("No posts found!");
                return Collections.emptyList(); // return empty list instead of null
            }

            return allPost;

        } catch (Exception e) {
            logger.error("Error in getAllPost: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public PostModel deletespecificPost(Long postId) {
        try {
            Optional<Post> delPost = postRepository.findById(postId);
            if (delPost.isPresent()) {
                postRepository.deleteById(postId);
                PostModel model = new PostModel(); // FIX
                BeanUtils.copyProperties(delPost.get(), model);
                logger.info("Deleted post: {}", model);
                return model;
            } else {
                logger.warn("No post found to delete with ID: {}", postId);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error in deletespecificPost: {}", e.getMessage(), e);
            return null;
        }
    }

    public String deleteAllPost() {
        try {
            postRepository.deleteAll();
            return "Delete Sucessfully All Post";

        } catch (Exception e) {
            logger.info("SomeThing Went Wrong: {}", e.getMessage());
            return null;
        }
    }

    public void getCountLikeId(Runnable task, boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCountLikeId'");
    }

  

}
