package com.hammasir.routingreport.repository;
;
import com.hammasir.routingreport.model.entity.AccidentReport;
import com.hammasir.routingreport.model.entity.WeatherReport;
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

//    @Query("SELECT wr FROM WeatherReport wr WHERE ST_Equals(wr.location, ST_GeomFromText(:location)) " +
//            "AND wr.expirationTime > CURRENT_TIMESTAMP")
//    Optional<WeatherReport> findByLocationAndExpirationTime(@Param("location") String location);

    @Query("SELECT ar FROM WeatherReport ar WHERE ST_Intersects(ar.location, ST_Buffer(:location, 10 * 0.00001)) = true " +
            "AND ar.expirationTime > CURRENT_TIMESTAMP")
    List<WeatherReport> findActive(@Param("location") Geometry location);
}
