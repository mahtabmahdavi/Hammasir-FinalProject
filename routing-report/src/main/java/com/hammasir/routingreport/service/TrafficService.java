package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.TrafficReport;
import com.hammasir.routingreport.model.enums.Traffic;
import com.hammasir.routingreport.repository.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;
    private final ObjectMapper objectMapper;

    public ReportDto createTrafficReport(ReportDto report) {
        boolean isExisted = trafficRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            TrafficReport newReport = new TrafficReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setCategory(Traffic.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            trafficRepository.save(newReport);
            return objectMapper.convertValue(newReport, ReportDto.class);
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActiveTrafficReport(ReportDto report) {
        return null;
    }
}
