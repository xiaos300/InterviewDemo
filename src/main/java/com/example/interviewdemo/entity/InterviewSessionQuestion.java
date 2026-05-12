package com.example.interviewdemo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "interview_session_question")
public class InterviewSessionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sessionId;
    private Long questionId;
    private int questionOrder;
    private LocalDateTime createTime;
    @PrePersist
    public void prePersist(){
        this.createTime = LocalDateTime.now();
    }
}
