package com.example.interviewdemo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密类，使用BCrypt 加密算法
 * 一个加密方法
 * 一个匹配方法
 */
public class PasswordUtils {
    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(); // 创建加密器，全局唯一，线程安全，性能高，不可逆

    /**
     * 加密方法
     * @param rawPassword
     * @return
     */
    public static String encode(String rawPassword) {
        return PASSWORD_ENCODER.encode(rawPassword);
    } // 加密

    /**
     * 密码对比
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public static boolean matches(String rawPassword, String encodedPassword){
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword); // 匹配
    }
}
