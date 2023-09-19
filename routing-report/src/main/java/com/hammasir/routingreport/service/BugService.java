package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ApprovalDTO;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.enums.Bug;
import com.hammasir.routingreport.repository.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BugService {

    private final BugRepository bugRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public CreationDTO convertToReportDto(BugReport report) {
        return CreationDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public CreationDTO createBugReport(CreationDTO report) {
        boolean isExisted = bugRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            BugReport newReport = new BugReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(false);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusMonths(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Bug.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(bugRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public CreationDTO getActiveBugReport(CreationDTO report) {
        return null;
    }


    public CreationDTO approveBugReport(ApprovalDTO approvedReport) {
        Optional<BugReport> desiredReport = bugRepository.findById(approvedReport.getReportId());
        if (desiredReport.isPresent()) {
            BugReport report = desiredReport.get();
            report.setIsApproved(true);
            return convertToReportDto(bugRepository.save(report));
        } else {
            throw new IllegalArgumentException("Report is NOT found!");
        }
    }
}
