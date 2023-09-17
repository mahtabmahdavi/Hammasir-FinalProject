package com.hammasir.routingreport.repository;
;
import com.hammasir.routingreport.model.entity.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherReport, Long> {

    @Query("SELECT * FROM WeatherReport wr WHERE ST_Equals(wr.location, ST_GeomFromText(:location)) " +
            "AND wr.expirationTime.isAfter(:expirationTime)")
    Optional<WeatherReport> findByLocationAndExpirationTime(@Param("location") String location,
                                                            @Param("expirationTime") LocalDateTime expirationTime);
}