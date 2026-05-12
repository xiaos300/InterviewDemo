package com.example.interviewdemo.repository;

import com.example.interviewdemo.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
