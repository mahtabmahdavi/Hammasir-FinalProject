package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.TrafficReport;
import com.hammasir.routingreport.model.enums.Traffic;
import com.hammasir.routingreport.repository.TrafficRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public TrafficService(TrafficRepository trafficRepository, GeometryFactory geometryFactory,
                          UserFactory userFactory) {
        this.trafficRepository = trafficRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createTrafficReport(ReportDto report) {
        Optional<TrafficReport> desiredReport = trafficRepository.findByLocationAndExpirationTime(
                report.getLocation(), LocalDateTime.now());
        if (desiredReport.isEmpty()) {
            TrafficReport newReport = new TrafficReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(10);
            newReport.setExpirationTime(LocalDateTime.now().plusMinutes(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(userFactory.findUser(report));
            switch (report.getCategory()) {
                case "JAM" -> newReport.setCategory(Traffic.JAM);
                case "SMOOTH" -> newReport.setCategory(Traffic.SMOOTH);
                case "SEMI_HEAVY" -> newReport.setCategory(Traffic.SEMI_HEAVY);
                default -> throw new IllegalArgumentException("Category of traffic report is NOT valid!");
            }
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
}
