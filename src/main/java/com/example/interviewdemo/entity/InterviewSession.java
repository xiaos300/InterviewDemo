package com.example.interviewdemo.entity;

import com.example.interviewdemo.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "interview_session")
public class InterviewSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String title;
    @Enumerated(EnumType.STRING) // 将枚举以字符串形式存到数据库，否则默认会存数字0 1 2
    private SessionStatus status = SessionStatus.NOT_STARTED; // 默认未开始
    private int questionCount;
    private LocalDateTime startTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @PrePersist
    public void prePersist(){
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.updateTime = LocalDateTime.now();
    }
}
