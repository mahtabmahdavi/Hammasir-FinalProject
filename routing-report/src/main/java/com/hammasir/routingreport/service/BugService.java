package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.enums.Bug;
import com.hammasir.routingreport.repository.BugRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BugService {

    private final BugRepository bugRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public BugService(BugRepository bugRepository, GeometryFactory geometryFactory,
                      AuthenticationService authService) {
        this.bugRepository = bugRepository;
        this.geometryFactory = geometryFactory;
        this.authService = authService;
    }

    public ReportDto createBugReport(ReportDto report) {
        Optional<BugReport> desiredReport = bugRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            BugReport newReport = new BugReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusMonths(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            newReport.setCategory(Bug.fromValue(report.getCategory()));
            bugRepository.save(newReport);
            return ReportDto.builder()
                    .category(newReport.getCategory().name())
                    .location(geometryFactory.createWkt(newReport.getLocation()))
                    .type(newReport.getType())
                    .userId(newReport.getUser().getId())
                    .build();
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActiveBugReport(ReportDto report) {
        return null;
    }
}
