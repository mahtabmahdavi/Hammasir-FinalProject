package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventReport, Long> {
}
