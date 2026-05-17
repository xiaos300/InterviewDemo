package com.example.interviewdemo.service;

import com.example.interviewdemo.dto.LoginRequest;
import com.example.interviewdemo.dto.LoginResponse;
import com.example.interviewdemo.dto.RegisterRequest;
import com.example.interviewdemo.dto.UserInfoResponse;
import com.example.interviewdemo.entity.User;
import com.example.interviewdemo.exception.BusinessException;
import com.example.interviewdemo.repository.UserRepository;
import com.example.interviewdemo.utils.JwtUtils;
import com.example.interviewdemo.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    public void register(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new BusinessException("该用户名已存在");
        }else {
            User user = new User();
            user.setNickname(request.getNickname());
            user.setUsername(request.getUsername());
            user.setPassword(PasswordUtils.encode(request.getPassword()));
            User save = userRepository.save(user);
        }
    }
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null){
            throw new BusinessException("用户名不存在");
        }
        if (PasswordUtils.matches(request.getPassword(), user.getPassword())){
            // 如果密码相匹配则登陆成功
            // 用Jwt生成token
            String token = JwtUtils.generateToken(user.getId(), user.getUsername());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsername(user.getUsername());
            loginResponse.setNickname(user.getNickname());
            loginResponse.setLastLoginTime(user.getLastLoginTime());
            loginResponse.setToken(token);
            // 当前登录时间设置为最后登录时间
            user.setLastLoginTime(LocalDateTime.now());
            userRepository.save(user);
            return loginResponse;
        }
        throw new BusinessException("账号或密码错误");

    }
    public UserInfoResponse getCurrentUserInfo(String token){
        if (token == null || token.isEmpty()){
            throw new BusinessException("token为空");
        }
        if (token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        if (!JwtUtils.validateToken(token)){
            throw new BusinessException("token异常");
        }
        String username = JwtUtils.getUsername(token);
        User user = userRepository.findByUsername(username);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setNickname(user.getNickname());
        userInfoResponse.setUsername(user.getUsername());
        userInfoResponse.setCreateTime(user.getCreateTime());
        userInfoResponse.setUpdateTime(user.getUpdateTime());
        userInfoResponse.setLastLoginTime(user.getLastLoginTime());
        return userInfoResponse;

    }
    public void logout(){
        // 登出占位，先不实现
        // todo：后端登出
    }

}
