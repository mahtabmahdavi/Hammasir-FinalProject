package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.PoliceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PoliceRepository extends JpaRepository<PoliceReport, Long> {

    @Query("SELECT * FROM PoliceReport pr WHERE ST_Equals(pr.location, ST_GeomFromText(:location)) " +
            "AND pr.expirationTime.isAfter(:expirationTime)")
    Optional<PoliceReport> findByLocationAndExpirationTime(@Param("location") String location,
                                                           @Param("expirationTime") LocalDateTime expirationTime);
}
