package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.BumpReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BumpRepository extends JpaRepository<BumpReport, Long> {

    @Query("SELECT br FROM BumpReport br WHERE ST_Equals(br.location, ST_GeomFromText(:location)) " +
            "AND br.expirationTime > CURRENT_TIMESTAMP")
    Optional<BumpReport> findByLocationAndExpirationTime(@Param("location") String location);
}
