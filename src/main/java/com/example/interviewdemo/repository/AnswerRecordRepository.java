package com.example.interviewdemo.repository;

import com.example.interviewdemo.entity.AnswerRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRecordRepository extends JpaRepository<AnswerRecord, Long> {
}
