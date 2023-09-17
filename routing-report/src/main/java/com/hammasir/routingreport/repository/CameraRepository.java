package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.CameraReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<CameraReport, Long> {

    @Query("SELECT * FROM CameraReport cr WHERE ST_Equals(cr.location, ST_GeomFromText(:location)) " +
            "AND cr.expirationTime.isAfter(:expirationTime)")
    Optional<CameraReport> findByLocationAndExpirationTime(@Param("location") String location,
                                                           @Param("expirationTime") LocalDateTime expirationTime);
}
