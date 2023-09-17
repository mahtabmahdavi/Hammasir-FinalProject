package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.PoliceReport;
import com.hammasir.routingreport.model.enums.Police;
import com.hammasir.routingreport.repository.PoliceRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class PoliceService {

    private final PoliceRepository policeRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public PoliceService(PoliceRepository policeRepository, GeometryFactory geometryFactory,
                         UserFactory userFactory) {
        this.policeRepository = policeRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createPoliceReport(ReportDto report) {
        Optional<PoliceReport> desiredReport = policeRepository.findByLocationAndExpirationTime(
                report.getLocation(), LocalDateTime.now());
        if (desiredReport.isEmpty()) {
            PoliceReport newReport = new PoliceReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(userFactory.findUser(report));
            switch (report.getCategory()) {
                case "POLICE" -> newReport.setCategory(Police.POLICE);
                case "SECRET_POLICE" -> newReport.setCategory(Police.SECRET_POLICE);
                case "OPPOSITE_LINE" -> newReport.setCategory(Police.OPPOSITE_LINE);
                default -> throw new IllegalArgumentException("Category of police report is NOT valid!");
            }
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
}
