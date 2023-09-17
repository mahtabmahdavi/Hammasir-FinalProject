package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.AccidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccidentRepository extends JpaRepository<AccidentReport, Long> {

    @Query("SELECT * FROM AccidentReport ar WHERE ST_Equals(ar.location, ST_GeomFromText(:location)) " +
            "AND ar.expirationTime.isAfter(:expirationTime)")
    Optional<AccidentReport> findByLocationAndExpirationTime(@Param("location") String location,
                                                             @Param("expirationTime") LocalDateTime expirationTime);
//    @Query("SELECT ar FROM AccidentReport ar WHERE ar.user = :user")
//    List<AccidentReport> findByUser(@Param("user") User user);
}
