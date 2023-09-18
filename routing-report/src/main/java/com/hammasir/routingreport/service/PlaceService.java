package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.PlaceReport;
import com.hammasir.routingreport.model.enums.Place;
import com.hammasir.routingreport.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public PlaceService(PlaceRepository placeRepository, GeometryFactory geometryFactory,
                        AuthenticationService authService) {
        this.placeRepository = placeRepository;
        this.geometryFactory = geometryFactory;
        this.authService  = authService;
    }

    public ReportDto createPlaceReport(ReportDto report) {
        Optional<PlaceReport> desiredReport = placeRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            PlaceReport newReport = new PlaceReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            newReport.setCategory(Place.fromValue(report.getCategory()));
            placeRepository.save(newReport);
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

    public ReportDto getActivePlaceReport(ReportDto report) {
        return null;
    }
}
