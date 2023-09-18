package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.AccidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccidentRepository extends JpaRepository<AccidentReport, Long> {

    @Query("SELECT ar FROM AccidentReport ar WHERE ST_Equals(ar.location, ST_GeomFromText(:location)) " +
            "AND ar.expirationTime > CURRENT_TIMESTAMP")
    Optional<AccidentReport> findByLocationAndExpirationTime(@Param("location") String location);

    @Query("SELECT ar FROM AccidentReport ar WHERE ST_Intersects(ar.location, ST_Buffer(:location, 10 * 0.00001)) = true " +
            "AND ar.expirationTime > CURRENT_TIMESTAMP")
    List<AccidentReport> findActive();
//    @Query("SELECT ar FROM AccidentReport ar WHERE ar.user = :user")
//    List<AccidentReport> findByUser(@Param("user") User user);
}
