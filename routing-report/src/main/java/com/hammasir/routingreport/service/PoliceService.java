package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.PoliceReport;
import com.hammasir.routingreport.model.enums.Police;
import com.hammasir.routingreport.repository.PoliceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PoliceService {

    private final PoliceRepository policeRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public PoliceService(PoliceRepository policeRepository, GeometryFactory geometryFactory,
                         AuthenticationService authService) {
        this.policeRepository = policeRepository;
        this.geometryFactory = geometryFactory;
        this.authService = authService;
    }

    public ReportDto createPoliceReport(ReportDto report) {
        Optional<PoliceReport> desiredReport = policeRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            PoliceReport newReport = new PoliceReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            newReport.setCategory(Police.fromValue(report.getCategory()));
            policeRepository.save(newReport);
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

    public ReportDto getActivePoliceReport(ReportDto report) {
        return null;
    }
}
