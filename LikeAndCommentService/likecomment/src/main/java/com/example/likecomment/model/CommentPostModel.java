package com.example.likecomment.model;

import java.sql.Timestamp;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentPostModel {

    private Long commentId;

    private String commentMsg;

    // @ManyToOne
    @Transient
    private UserSendPostModel commentedUser;

    private Long commentedUserId;

    private boolean isActiveComment;
    private String commentTime;
    private String commentCreatedBy;
    
}
