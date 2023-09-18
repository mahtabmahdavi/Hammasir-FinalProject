package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BugRepository extends JpaRepository<BugReport, Long> {

    @Query("SELECT br FROM BugReport br WHERE ST_Equals(br.location, ST_GeomFromText(:location)) " +
            "AND br.expirationTime > CURRENT_TIMESTAMP")
    Optional<BugReport> findByLocationAndExpirationTime(@Param("location") String location);
}
