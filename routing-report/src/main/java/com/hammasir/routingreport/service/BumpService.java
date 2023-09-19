package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.entity.BumpReport;
import com.hammasir.routingreport.repository.BumpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BumpService {

    private final BumpRepository bumpRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public ReportDto convertToReportDto(BumpReport report) {
        return ReportDto.builder()
                .type(report.getType())
                .category(null)
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public ReportDto createBumpReport(ReportDto report) {
        boolean isExisted = bumpRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            BumpReport newReport = new BumpReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(bumpRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActiveBumpReport(ReportDto report) {
        return null;
    }
}
