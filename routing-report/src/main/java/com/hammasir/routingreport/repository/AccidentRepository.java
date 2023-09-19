package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.AccidentReport;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccidentRepository extends JpaRepository<AccidentReport, Long> {

    @Query("SELECT CASE WHEN COUNT(ar) > 0 THEN true ELSE false END " +
            "FROM AccidentReport ar WHERE ST_Equals(ar.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND ar.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

    @Query("SELECT ar FROM AccidentReport ar WHERE ST_DWithin(ST_Transform(ar.location, 3857), ST_Transform(:location, 3857), 10) = true " +
            "AND ar.expirationTime > CURRENT_TIMESTAMP")
    List<AccidentReport> findActive(@Param("location") Geometry location);
}
