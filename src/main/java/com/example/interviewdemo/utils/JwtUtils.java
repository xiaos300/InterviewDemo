package com.example.interviewdemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * 主要方法：
 * 生成token
 * 解析token
 * 判断token是否有效
 */
public class JwtUtils {
    // 秘钥
    private static final String SECRET = "Interview-demo-project-secret-test";
    // 过期时间（24小时）
    private static final long EXPIRATION = 1000L*60*60*24;
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    public static String generateToken(Long userId, String username){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRATION);
        return Jwts.builder()
                .subject(username)             // 主题、用户名
                .claim("userId", userId) // 自定义数据
                .issuedAt(now)                  // 签发时间
                .expiration(expireDate)         // 过期时间
                .signWith(KEY)                  // 签名加密
                .compact();                     // 拼接
    }

    public static Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)// PS:当token无效时直接抛出异常，而不是返回false
                .getPayload();
    }

    public static boolean validateToken(String token){
        try{
            parseToken(token);      // 当token无效时JWT会直接抛出异常
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public static String getUsername(String token){
        return parseToken(token).getSubject();
    }
    public static Long getUserId(String token){
        return parseToken(token).get("userId", Long.class);
    }

}
