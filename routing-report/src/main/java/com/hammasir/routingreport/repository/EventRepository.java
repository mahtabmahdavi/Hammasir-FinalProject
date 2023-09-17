package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventReport, Long> {

    @Query("SELECT * FROM EventReport er WHERE ST_Equals(er.location, ST_GeomFromText(:location)) " +
            "AND er.expirationTime.isAfter(:expirationTime)")
    Optional<EventReport> findByLocationAndExpirationTime(@Param("location") String location,
                                                          @Param("expirationTime") LocalDateTime expirationTime);
}
