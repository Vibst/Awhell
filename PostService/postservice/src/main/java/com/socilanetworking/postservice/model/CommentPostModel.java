package com.socilanetworking.postservice.model;

import java.sql.Timestamp;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Transient;

public class CommentPostModel {

    private Long commentId;

    private String commentMsg;

    // @ManyToOne
    @Transient
    private UserSendPostModel commentedUser;

    private Long commentedUserId;

    private boolean isActiveComment;
    private Timestamp commentTime;
    private String commentCreatedBy;
    
}
