package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<BugReport, Long> {
}
