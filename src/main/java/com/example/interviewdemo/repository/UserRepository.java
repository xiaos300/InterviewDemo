package com.example.interviewdemo.repository;

import com.example.interviewdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * Spring Data JPA 有一个“方法名派生查询”机制，在启动时会根据方法名自动实现对应的方法
     * @param username
     * @return
     */
    public boolean existsByUsername(String username);
    public User findByUsername(String username);
}
