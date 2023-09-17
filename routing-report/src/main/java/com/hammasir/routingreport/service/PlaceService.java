package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.PlaceReport;
import com.hammasir.routingreport.model.enums.Place;
import com.hammasir.routingreport.repository.PlaceRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class PlaceService {

    private final PlaceRepository placeRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public PlaceService(PlaceRepository placeRepository, GeometryFactory geometryFactory,
                        UserFactory userFactory) {
        this.placeRepository = placeRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createPlaceReport(ReportDto report) {
        Optional<PlaceReport> desiredReport = placeRepository.findByLocationAndExpirationTime(
                report.getLocation(), LocalDateTime.now());
        if (desiredReport.isEmpty()) {
            PlaceReport newReport = new PlaceReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(userFactory.findUser(report));
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
}
