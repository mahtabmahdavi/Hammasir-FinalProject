package com.hammasir.routingreport.service.report;

import com.hammasir.routingreport.component.GeometryHandler;
import com.hammasir.routingreport.model.DTO.ChangeDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.model.enumuration.Bug;
import com.hammasir.routingreport.repository.BugRepository;
import com.hammasir.routingreport.service.AuthenticationService;
import com.hammasir.routingreport.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BugReportService implements ReportService {

    private final BugRepository bugRepository;
    private final AuthenticationService authenticationService;
    private final GeometryHandler geometryHandler;

    public ReportDTO convertToReportDto(BugReport report) {
        return ReportDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryHandler.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    @Override
    public ReportDTO createReport(ReportDTO report) {
        boolean isExisted = bugRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            BugReport newReport = new BugReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(false);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusMonths(newReport.getDuration()));
            newReport.setLocation(geometryHandler.createGeometry(report.getLocation()));
            newReport.setCategory(Bug.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(bugRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    @Override
    public List<ReportDTO> getActiveReports(String location) {
        List<BugReport> activeReports = bugRepository.findByLocationAndExpirationTimeAndIsApproved(geometryHandler.createGeometry(location));
        return activeReports.stream()
                .map(this::convertToReportDto)
                .collect(Collectors.toList());
    }
    @Override
    public ReportDTO likeReport(ChangeDTO likedReport) {
        BugReport report = bugRepository.findById(likedReport.getReportId())
                .orElseThrow(() -> new IllegalArgumentException("Report is NOT found!"));
        List<Long> contributors = report.getContributors();
        User desiredUser = authenticationService.findUser(likedReport.getUsername());
        if (contributors.contains(desiredUser.getId())) {
            throw new IllegalArgumentException("User has already liked or disliked this report.");
        } else {
            contributors.add(desiredUser.getId());
            report.setContributors(contributors);
            Duration durationToAdd = likedReport.isStatus() ? Duration.ofDays(3) : Duration.ofDays(-3);
            report.setExpirationTime(report.getExpirationTime().plus(durationToAdd));
            return convertToReportDto(bugRepository.save(report));
        }
    }

    @Override
    public ReportDTO approveReport(ChangeDTO changedReport) {
        Optional<BugReport> desiredReport = bugRepository.findById(changedReport.getReportId());
        if (desiredReport.isPresent()) {
            BugReport report = desiredReport.get();
            report.setIsApproved(changedReport.isStatus());
            return convertToReportDto(bugRepository.save(report));
        } else {
            throw new IllegalArgumentException("Report is NOT found!");
        }
    }
}
