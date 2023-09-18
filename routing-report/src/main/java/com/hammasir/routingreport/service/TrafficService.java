package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.TrafficReport;
import com.hammasir.routingreport.model.enums.Traffic;
import com.hammasir.routingreport.repository.TrafficRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public TrafficService(TrafficRepository trafficRepository, GeometryFactory geometryFactory,
                          AuthenticationService authService) {
        this.trafficRepository = trafficRepository;
        this.geometryFactory = geometryFactory;
        this.authService = authService;
    }

    public ReportDto createTrafficReport(ReportDto report) {
        Optional<TrafficReport> desiredReport = trafficRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            TrafficReport newReport = new TrafficReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(10);
            newReport.setExpirationTime(LocalDateTime.now().plusMinutes(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            newReport.setCategory(Traffic.fromValue(report.getCategory()));
            trafficRepository.save(newReport);
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

    public ReportDto getActiveTrafficReport(ReportDto report) {
        return null;
    }
}
