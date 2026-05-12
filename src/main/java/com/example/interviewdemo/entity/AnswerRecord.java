package com.example.interviewdemo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "answer_record")
public class AnswerRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sessionId;
    private Long sessionQuestionId;
    private Long userId;
    @Column(columnDefinition = "TEXT")
    private String answerText;
    private int answerDurationSeconds;
    private int score; // 打分
    private LocalDateTime createTime;
    @PrePersist
    public void prePersist(){
        this.createTime = LocalDateTime.now();
    }
}
