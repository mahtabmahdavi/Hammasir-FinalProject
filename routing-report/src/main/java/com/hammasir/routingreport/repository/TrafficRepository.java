package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.TrafficReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TrafficRepository extends JpaRepository<TrafficReport, Long> {

    @Query("SELECT * FROM TrafficReport tr WHERE ST_Equals(tr.location, ST_GeomFromText(:location)) " +
            "AND tr.expirationTime.isAfter(:expirationTime)")
    Optional<TrafficReport> findByLocationAndExpirationTime(@Param("location") String location,
                                                            @Param("expirationTime") LocalDateTime expirationTime);
}
