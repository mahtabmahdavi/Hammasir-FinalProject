package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.TrafficReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends JpaRepository<TrafficReport, Long> {

    @Query("SELECT CASE WHEN COUNT(tr) > 0 THEN true ELSE false END " +
            "FROM TrafficReport tr WHERE ST_Equals(tr.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND tr.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

//    @Query("SELECT tr FROM TrafficReport tr WHERE ST_Equals(tr.location, ST_GeomFromText(:location)) " +
//            "AND tr.expirationTime > CURRENT_TIMESTAMP")
//    Optional<TrafficReport> findByLocationAndExpirationTime(@Param("location") String location);
}
