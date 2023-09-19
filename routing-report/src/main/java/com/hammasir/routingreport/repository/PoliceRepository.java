package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.PoliceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceRepository extends JpaRepository<PoliceReport, Long> {

    @Query("SELECT CASE WHEN COUNT(pr) > 0 THEN true ELSE false END " +
            "FROM PoliceReport pr WHERE ST_Equals(pr.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND pr.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

//    @Query("SELECT pr FROM PoliceReport pr WHERE ST_Equals(pr.location, ST_GeomFromText(:location)) " +
//            "AND pr.expirationTime > CURRENT_TIMESTAMP")
//    Optional<PoliceReport> findByLocationAndExpirationTime(@Param("location") String location);
}
