package com.socilanetworking.postservice.model;



import jakarta.persistence.Transient;

public class LikePostModel {
     private Long likeId;

    private boolean isLike;
    private boolean isUnLike;
    private Integer countLike;
    private Integer countUnLike;

    // @ManyToOne
    @Transient
    private UserSendPostModel userLike;

    private Long userLikeId;
    private boolean likeUserActive;
    private boolean unLikeUserActive;
}
