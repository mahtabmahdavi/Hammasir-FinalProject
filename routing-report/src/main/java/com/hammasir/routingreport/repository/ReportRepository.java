package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.Report;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ReportRepository extends JpaRepository<Report, Long> {
}
