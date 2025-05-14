package com.example.likecomment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.likecomment.entity.LikePost;

@Repository
public interface LikeAndCommentRepository extends CrudRepository<LikePost, Long> {

}
