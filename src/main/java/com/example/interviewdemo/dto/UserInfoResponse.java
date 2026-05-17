package com.example.interviewdemo.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserInfoResponse {
    private String username; // 用户名
    private String nickname; // 昵称
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    private LocalDateTime lastLoginTime; // 上次登录时间
}
