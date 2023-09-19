package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ApprovalDTO;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.entity.BumpReport;
import com.hammasir.routingreport.repository.BumpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BumpService {

    private final BumpRepository bumpRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public CreationDTO convertToReportDto(BumpReport report) {
        return CreationDTO.builder()
                .type(report.getType())
                .category(null)
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public CreationDTO createBumpReport(CreationDTO report) {
        boolean isExisted = bumpRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            BumpReport newReport = new BumpReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(false);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(bumpRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public CreationDTO getActiveBumpReport(CreationDTO report) {
        return null;
    }

    public CreationDTO approveBumpReport(ApprovalDTO approvedReport) {
        Optional<BumpReport> desiredReport = bumpRepository.findById(approvedReport.getReportId());
        if (desiredReport.isPresent()) {
            BumpReport report = desiredReport.get();
            report.setIsApproved(true);
            return convertToReportDto(bumpRepository.save(report));
        } else {
            throw new IllegalArgumentException("Report is NOT found!");
        }
    }
}
