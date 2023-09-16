package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.CameraReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<CameraReport, Long> {
}
