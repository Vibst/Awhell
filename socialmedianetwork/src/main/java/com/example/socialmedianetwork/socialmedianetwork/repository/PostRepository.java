package com.example.socialmedianetwork.socialmedianetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.socialmedianetwork.socialmedianetwork.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}
