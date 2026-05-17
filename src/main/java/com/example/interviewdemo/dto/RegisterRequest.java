package com.example.interviewdemo.dto;

import com.example.interviewdemo.entity.User;
import lombok.Data;

/**
 * 注册
 */
@Data
public class RegisterRequest {
    private String username; // 用户名
    private String password; // 密码
    private String nickname; // 昵称
}
