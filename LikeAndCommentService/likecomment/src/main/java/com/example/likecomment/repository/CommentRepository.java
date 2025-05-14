package com.example.likecomment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.likecomment.entity.CommentPost;

@Repository
public interface CommentRepository extends CrudRepository<CommentPost, Long>{}