package com.socilanetworking.postservice.entity;

import java.sql.Timestamp;
import java.util.List;

import com.socilanetworking.postservice.model.CommentPostModel;
import com.socilanetworking.postservice.model.LikePostModel;
import com.socilanetworking.postservice.model.UserSendPostModel;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    private String content;
    private String image;
    private String vdo;
    private String rec;
    private Long userPostId;
    private Long likePostId;
    private Long commentPostid;
    private Timestamp postTime;

    @Transient
    private UserSendPostModel userPost;

    @Transient
    private List<LikePostModel> likePosts;

    @Transient
    private List<CommentPostModel> commentPosts;

    private String sharePost;
}
