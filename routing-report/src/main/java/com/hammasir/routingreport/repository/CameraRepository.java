package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.CameraReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<CameraReport, Long> {

    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN true ELSE false END " +
            "FROM CameraReport cr WHERE ST_Equals(cr.location, ST_GeomFromText(:location)) " +
            "AND cr.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

//    @Query("SELECT cr FROM CameraReport cr WHERE ST_Equals(cr.location, ST_GeomFromText(:location)) " +
//            "AND cr.expirationTime > CURRENT_TIMESTAMP")
//    Optional<CameraReport> findByLocationAndExpirationTime(@Param("location") String location);
}
