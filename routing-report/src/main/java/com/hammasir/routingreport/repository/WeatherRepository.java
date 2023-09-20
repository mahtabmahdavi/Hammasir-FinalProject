package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.report.WeatherReport;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherReport, Long> {

    @Query("SELECT CASE WHEN COUNT(wr) > 0 THEN true ELSE false END " +
            "FROM WeatherReport wr WHERE ST_Equals(wr.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND wr.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

    @Query("SELECT wr FROM WeatherReport wr " +
            "WHERE ST_DWithin(ST_Transform(wr.location, 3857), ST_Transform(:location, 3857), 10) = true " +
            "AND wr.expirationTime > CURRENT_TIMESTAMP " +
            "AND wr.isApproved = true")
    List<WeatherReport> findByLocationAndExpirationTimeAndIsApproved(@Param("location") Geometry location);
}
