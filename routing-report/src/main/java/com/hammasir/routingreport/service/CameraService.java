package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BugReport;
import com.hammasir.routingreport.model.entity.CameraReport;
import com.hammasir.routingreport.model.enums.Camera;
import com.hammasir.routingreport.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public ReportDto convertToReportDto(CameraReport report) {
        return ReportDto.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public ReportDto createCameraReport(ReportDto report) {
        boolean isExisted = cameraRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            CameraReport newReport = new CameraReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Camera.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));

            return convertToReportDto(cameraRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActiveCameraReport(ReportDto report) {
        return null;
    }
}
