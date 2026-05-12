package com.example.interviewdemo.repository;

import com.example.interviewdemo.entity.InterviewSession;
import com.example.interviewdemo.entity.InterviewSessionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewSessionQuestionRepository extends JpaRepository<InterviewSessionQuestion, Long> {
}
