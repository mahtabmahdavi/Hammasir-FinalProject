package com.hammasir.routingreport.service.report;

import com.hammasir.routingreport.component.GeometryHandler;
import com.hammasir.routingreport.model.DTO.ChangeDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import com.hammasir.routingreport.model.entity.PlaceReport;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.model.enumuration.Place;
import com.hammasir.routingreport.repository.PlaceRepository;
import com.hammasir.routingreport.service.AuthenticationService;
import com.hammasir.routingreport.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceReportService implements ReportService {

    private final PlaceRepository placeRepository;
    private final AuthenticationService authenticationService;
    private final GeometryHandler geometryHandler;

    public ReportDTO convertToReportDto(PlaceReport report) {
        return ReportDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryHandler.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    @Override
    public ReportDTO createReport(ReportDTO report) {
        boolean isExisted = placeRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            PlaceReport newReport = new PlaceReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(false);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setLocation(geometryHandler.createGeometry(report.getLocation()));
            newReport.setCategory(Place.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(placeRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    @Override
    public List<ReportDTO> getActiveReports(String location) {
        List<PlaceReport> activeReports = placeRepository.findByIsApprovedAndLocation(geometryHandler.createGeometry(location));
        return activeReports.stream()
                .map(this::convertToReportDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO likeReport(ChangeDTO likedReport) {
        PlaceReport report = placeRepository.findById(likedReport.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report is NOT found!"));
        List<Long> contributors = report.getContributors();
        User desiredUser = authenticationService.findUser(likedReport.getUsername());
        if (contributors.contains(desiredUser.getId())) {
            throw new IllegalArgumentException("User has already liked or disliked this report.");
        } else {
            contributors.add(desiredUser.getId());
            report.setContributors(contributors);
            Duration durationToAdd = likedReport.isStatus() ? Duration.ofDays(36) : Duration.ofDays(-36);
            report.setExpirationTime(report.getExpirationTime().plus(durationToAdd));
            return convertToReportDto(placeRepository.save(report));
        }
    }

    @Override
    public ReportDTO approveReport(ChangeDTO changedReport) {
        Optional<PlaceReport> desiredReport = placeRepository.findById(changedReport.getReportId());
        if (desiredReport.isPresent()) {
            PlaceReport report = desiredReport.get();
            report.setIsApproved(changedReport.isStatus());
            return convertToReportDto(placeRepository.save(report));
        } else {
            throw new IllegalArgumentException("Report is NOT found!");
        }
    }
}
