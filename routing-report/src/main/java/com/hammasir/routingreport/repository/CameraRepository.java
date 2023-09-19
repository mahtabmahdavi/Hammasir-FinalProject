package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.CameraReport;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CameraRepository extends JpaRepository<CameraReport, Long> {

    Optional<CameraReport> findById(long id);

    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN true ELSE false END " +
            "FROM CameraReport cr WHERE ST_Equals(cr.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND cr.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

    @Query("SELECT cr FROM CameraReport cr " +
            "WHERE ST_DWithin(ST_Transform(cr.location, 3857), ST_Transform(:location, 3857), 10) = true " +
            "AND cr.expirationTime > CURRENT_TIMESTAMP " +
            "AND cr.isApproved = true")
    List<CameraReport> findByLocationAndExpirationTimeAndIsApproved(@Param("location") Geometry location);

}
