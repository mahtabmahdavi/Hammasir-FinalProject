package com.hammasir.routingreport.repository;

import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.entity.BumpReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BumpRepository extends JpaRepository<BumpReport, Long> {

    Optional<BumpReport> findById(long id);

    @Query("SELECT CASE WHEN COUNT(br) > 0 THEN true ELSE false END " +
            "FROM BumpReport br WHERE ST_Equals(br.location, ST_GeomFromText(:location, 4326)) = true " +
            "AND br.expirationTime > CURRENT_TIMESTAMP")
    boolean existsByLocationAndExpirationTime(@Param("location") String location);

//    @Query("SELECT br FROM BumpReport br WHERE ST_Equals(br.location, ST_GeomFromText(:location)) " +
//            "AND br.expirationTime > CURRENT_TIMESTAMP")
//    Optional<BumpReport> findByLocationAndExpirationTime(@Param("location") String location);
}
