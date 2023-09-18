package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventReport, Long> {

    @Query("SELECT er FROM EventReport er WHERE ST_Equals(er.location, ST_GeomFromText(:location)) " +
            "AND er.expirationTime > CURRENT_TIMESTAMP")
    Optional<EventReport> findByLocationAndExpirationTime(@Param("location") String location);
}
