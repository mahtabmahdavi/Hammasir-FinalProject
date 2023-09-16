package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.PoliceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceRepository extends JpaRepository<PoliceReport, Long> {
}
