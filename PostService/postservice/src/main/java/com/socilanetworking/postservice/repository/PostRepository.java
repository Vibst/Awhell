package com.socilanetworking.postservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.socilanetworking.postservice.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    
}
