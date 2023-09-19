package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ApprovedDTO;
import com.hammasir.routingreport.model.dto.ReportDTO;
import com.hammasir.routingreport.model.entity.BumpReport;
import com.hammasir.routingreport.model.entity.CameraReport;
import com.hammasir.routingreport.model.enums.Camera;
import com.hammasir.routingreport.repository.CameraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraRepository cameraRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public ReportDTO convertToReportDto(CameraReport report) {
        return ReportDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public ReportDTO createCameraReport(ReportDTO report) {
        boolean isExisted = cameraRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            CameraReport newReport = new CameraReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(false);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Camera.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));

            return convertToReportDto(cameraRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDTO getActiveCameraReport(ReportDTO report) {
        return null;
    }


    public ReportDTO approveCameraReport(ApprovedDTO approvedReport) {
        Optional<CameraReport> desiredReport = cameraRepository.findById(approvedReport.getReportId());
        if (desiredReport.isPresent()) {
            CameraReport report = desiredReport.get();
            report.setIsApproved(true);
            return convertToReportDto(cameraRepository.save(report));
        } else {
            throw new IllegalArgumentException("Report is NOT found!");
        }
    }
}
