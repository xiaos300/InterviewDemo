package com.example.interviewdemo.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LoginResponse {
    private String username; // 用户名
    private String nickname;
    private LocalDateTime lastLoginTime; // 上次登录时间
    private String token;
}
