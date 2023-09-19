package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.entity.PlaceReport;
import com.hammasir.routingreport.model.enums.Place;
import com.hammasir.routingreport.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public ReportDto convertToReportDto(PlaceReport report) {
        return ReportDto.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public ReportDto createPlaceReport(ReportDto report) {
        boolean isExisted = placeRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            PlaceReport newReport = new PlaceReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Place.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(placeRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActivePlaceReport(ReportDto report) {
        return null;
    }
}
