package com.example.likecomment.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
@Table(name = "comment")
public class CommentPost {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long commentId;

    private String commentMsg;

    // @ManyToOne
    @Transient
    private UserSendPostModel commentedUser;

    private Long commentedUserId;

    private boolean activeComment;
    private String commentTime;
    private String commentCreatedBy;

}
