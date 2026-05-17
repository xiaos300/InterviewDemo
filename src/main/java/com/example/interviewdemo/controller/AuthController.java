package com.example.interviewdemo.controller;

import com.example.interviewdemo.common.Result;
import com.example.interviewdemo.dto.LoginRequest;
import com.example.interviewdemo.dto.LoginResponse;
import com.example.interviewdemo.dto.RegisterRequest;
import com.example.interviewdemo.dto.UserInfoResponse;
import com.example.interviewdemo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("register")
    public Result register(@RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);
        return Result.success("注册成功！");
    }
    @PostMapping("login")
    public Result login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponsen = authService.login(loginRequest);
        return Result.success(loginResponsen);
    }
    @Operation(summary = "获取当前用户信息")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("me")
    public Result me(@RequestHeader("Authorization") String authorization){
        // todo:解析到token，然后获取用户信息
        UserInfoResponse currentUserInfo = authService.getCurrentUserInfo(authorization);
        return Result.success(currentUserInfo);
    }
    @PostMapping("logout")
    public Result logout(){
        return null;
    }
}
