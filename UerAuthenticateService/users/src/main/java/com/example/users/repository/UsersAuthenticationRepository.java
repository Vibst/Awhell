package com.example.users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.users.entity.Users;

@Repository
public interface UsersAuthenticationRepository extends CrudRepository<Users, Integer> {
    
}
