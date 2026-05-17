package com.example.interviewdemo.repository;

import com.example.interviewdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public boolean existsByUsername(String username);
    public User findByUsername(String username);
}
