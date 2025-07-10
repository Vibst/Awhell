package com.example.likecomment.model;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikePostModel {
    private Long likeId;

    private boolean isLike;

    private Integer countLike;

    // @ManyToOne
    @Transient
    private UserSendPostModel userLike;

    private Integer userLikeId;
    private boolean likeUserActive;

    private boolean isActive;
}
