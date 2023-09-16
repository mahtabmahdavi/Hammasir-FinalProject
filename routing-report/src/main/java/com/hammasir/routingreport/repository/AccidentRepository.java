package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.AccidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentRepository extends JpaRepository<AccidentReport, Long> {
}
