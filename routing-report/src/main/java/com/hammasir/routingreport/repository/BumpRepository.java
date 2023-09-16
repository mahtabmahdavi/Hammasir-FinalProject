package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.BumpReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BumpRepository extends JpaRepository<BumpReport, Long> {
}
