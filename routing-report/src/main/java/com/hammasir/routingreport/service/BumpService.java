package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BumpReport;
import com.hammasir.routingreport.repository.BumpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BumpService {

    private final BumpRepository bumpRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public BumpService(BumpRepository bumpRepository, GeometryFactory geometryFactory,
                       AuthenticationService authService) {
        this.bumpRepository = bumpRepository;
        this.geometryFactory = geometryFactory;
        this.authService = authService;
    }

    public ReportDto createBumpReport(ReportDto report) {
        Optional<BumpReport> desiredReport = bumpRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            BumpReport newReport = new BumpReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            bumpRepository.save(newReport);
            return ReportDto.builder()
                    .category(null)
                    .location(geometryFactory.createWkt(newReport.getLocation()))
                    .type(newReport.getType())
                    .userId(newReport.getUser().getId())
                    .build();
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActiveBumpReport(ReportDto report) {
        return null;
    }
}
