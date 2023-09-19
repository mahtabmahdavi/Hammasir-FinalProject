package com.hammasir.routingreport.service.report;

import com.hammasir.routingreport.component.GeometryHandler;
import com.hammasir.routingreport.model.DTO.ChangeDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import com.hammasir.routingreport.model.entity.TrafficReport;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.model.enumuration.Traffic;
import com.hammasir.routingreport.repository.TrafficRepository;
import com.hammasir.routingreport.service.AuthenticationService;
import com.hammasir.routingreport.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrafficReportService implements ReportService {

    private final TrafficRepository trafficRepository;
    private final AuthenticationService authenticationService;
    private final GeometryHandler geometryHandler;

    public ReportDTO convertToReportDto(TrafficReport report) {
        return ReportDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryHandler.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    @Override
    public ReportDTO createReport(ReportDTO report) {
        boolean isExisted = trafficRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            TrafficReport newReport = new TrafficReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setDuration(10);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusMinutes(newReport.getDuration()));
            newReport.setLocation(geometryHandler.createGeometry(report.getLocation()));
            newReport.setCategory(Traffic.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(trafficRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    @Override
    public List<ReportDTO> getActiveReports(String location) {
        List<TrafficReport> activeReports = trafficRepository.findByIsApprovedAndLocation(geometryHandler.createGeometry(location));
        return activeReports.stream()
                .map(this::convertToReportDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO likeReport(ChangeDTO likedReport) {
        TrafficReport report = trafficRepository.findById(likedReport.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report is NOT found!"));
        List<Long> contributors = report.getContributors();
        User desiredUser = authenticationService.findUser(likedReport.getUsername());
        if (contributors.contains(desiredUser.getId())) {
            throw new IllegalArgumentException("User has already liked or disliked this report.");
        } else {
            contributors.add(desiredUser.getId());
            report.setContributors(contributors);
            Duration durationToAdd = likedReport.isStatus() ? Duration.ofMinutes(1) : Duration.ofMinutes(-1);
            report.setExpirationTime(report.getExpirationTime().plus(durationToAdd));
            return convertToReportDto(trafficRepository.save(report));
        }
    }

    @Override
    public ReportDTO approveReport(ChangeDTO changedReport) {
        throw new IllegalArgumentException("Approval is not supported for this type of report.");
    }
}