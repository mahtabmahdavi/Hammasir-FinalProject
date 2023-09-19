package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.PlaceReport;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceReport, Long> {

    Optional<PlaceReport> findById(long id);

    @Query("SELECT CASE WHEN COUNT(pr) > 0 THEN true ELSE false END " +
            "FROM PlaceReport pr WHERE ST_Equals(pr.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND pr.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

    @Query("SELECT pr FROM PlaceReport pr " +
            "WHERE ST_DWithin(ST_Transform(pr.location, 3857), ST_Transform(:location, 3857), 10) = true " +
            "AND pr.expirationTime > CURRENT_TIMESTAMP " +
            "AND pr.isApproved = true")
    List<PlaceReport> findByLocationAndExpirationTimeAndIsApproved(@Param("location") Geometry location);
}
