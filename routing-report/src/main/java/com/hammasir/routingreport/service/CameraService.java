package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.CameraReport;
import com.hammasir.routingreport.model.enums.Camera;
import com.hammasir.routingreport.repository.CameraRepository;
import com.hammasir.routingreport.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CameraService {

    private final CameraRepository cameraRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public CameraService(CameraRepository cameraRepository, GeometryFactory geometryFactory,
                         AuthenticationService authService) {
        this.cameraRepository = cameraRepository;
        this.geometryFactory = geometryFactory;
        this.authService = authService;
    }

    public ReportDto createCameraReport(ReportDto report) {
        Optional<CameraReport> desiredReport = cameraRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            CameraReport newReport = new CameraReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            newReport.setCategory(Camera.fromValue(report.getCategory()));
            cameraRepository.save(newReport);
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

    public ReportDto getActiveCameraReport(ReportDto report) {
        return null;
    }
}
