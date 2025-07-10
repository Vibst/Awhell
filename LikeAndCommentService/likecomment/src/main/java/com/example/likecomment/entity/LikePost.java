package com.example.likecomment.entity;

import com.example.likecomment.model.UserSendPostModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@Entity
@Table(name = "post_like")
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    private boolean isLike;

    private Integer countLike;

    // @ManyToOne
    @Transient
    private UserSendPostModel userLike;

    private Integer userLikeId;
    private boolean likeUserActive;

    private boolean isActive;

    private boolean priviousLike;
}
