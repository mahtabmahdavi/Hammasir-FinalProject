package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.BumpReport;
import com.hammasir.routingreport.repository.BumpRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class BumpService {

    private final BumpRepository bumpRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public BumpService(BumpRepository bumpRepository, GeometryFactory geometryFactory,
                       UserFactory userFactory) {
        this.bumpRepository = bumpRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createBumpReport(ReportDto report) {
        Optional<BumpReport> desiredReport = bumpRepository.findByLocationAndExpirationTime(
                report.getLocation(), LocalDateTime.now());
        if (desiredReport.isEmpty()) {
            BumpReport newReport = new BumpReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(userFactory.findUser(report));
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
}
