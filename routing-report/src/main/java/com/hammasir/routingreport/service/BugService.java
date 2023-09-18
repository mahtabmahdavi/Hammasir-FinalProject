package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.enums.Bug;
import com.hammasir.routingreport.repository.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BugService {

    private final BugRepository bugRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;
    private final ObjectMapper objectMapper;

    public ReportDto createBugReport(ReportDto report) {
        boolean isExisted = bugRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            BugReport newReport = new BugReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setCategory(Bug.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            bugRepository.save(newReport);
            return objectMapper.convertValue(newReport, ReportDto.class);
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActiveBugReport(ReportDto report) {
        return null;
    }
}
