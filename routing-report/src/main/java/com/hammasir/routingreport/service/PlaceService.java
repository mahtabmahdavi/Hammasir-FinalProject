package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ApprovedDTO;
import com.hammasir.routingreport.model.dto.ReportDTO;
import com.hammasir.routingreport.model.entity.BumpReport;
import com.hammasir.routingreport.model.entity.PlaceReport;
import com.hammasir.routingreport.model.enums.Place;
import com.hammasir.routingreport.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public ReportDTO convertToReportDto(PlaceReport report) {
        return ReportDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public ReportDTO createPlaceReport(ReportDTO report) {
        boolean isExisted = placeRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            PlaceReport newReport = new PlaceReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(false);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Place.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(placeRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDTO getActivePlaceReport(ReportDTO report) {
        return null;
    }

    public ReportDTO approvePlaceReport(ApprovedDTO approvedReport) {
        Optional<PlaceReport> desiredReport = placeRepository.findById(approvedReport.getReportId());
        if (desiredReport.isPresent()) {
            PlaceReport report = desiredReport.get();
            report.setIsApproved(true);
            return convertToReportDto(placeRepository.save(report));
        } else {
            throw new IllegalArgumentException("Report is NOT found!");
        }
    }
}
