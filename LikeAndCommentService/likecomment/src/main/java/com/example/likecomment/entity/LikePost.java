package com.example.likecomment.entity;

import com.example.likecomment.model.UserSendPostModel;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("isIsLike")
    private boolean isLike;

    private Integer countLike;

    // @ManyToOne
    @Transient
    private UserSendPostModel userLike;

    private Integer userLikeId;
    @JsonProperty("likeUserActive")
    private boolean likeUserActive;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("priviousLike")
    private boolean priviousLike;
}
