package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.PlaceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceReport, Long> {
}
