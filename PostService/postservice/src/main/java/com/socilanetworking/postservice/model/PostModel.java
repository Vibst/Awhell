package com.socilanetworking.postservice.model;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.List;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostModel implements Serializable {

    private Long postId;

    private String content;
    private String image;
    private String vdo;
    private String rec;

    private UserSendPostModel userPost;

    private Timestamp postTime;
    private List<LikePostModel> likePosts;

    private List<CommentPostModel> commentPosts;

    private String sharePost;

}
