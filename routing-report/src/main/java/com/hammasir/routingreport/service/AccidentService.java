package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.AccidentReport;
import com.hammasir.routingreport.model.enums.Accident;
import com.hammasir.routingreport.repository.AccidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public AccidentService(AccidentRepository accidentRepository, GeometryFactory geometryFactory,
                           UserFactory userFactory) {
        this.accidentRepository = accidentRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createAccidentReport(ReportDto report) {
        Optional<AccidentReport> desiredReport = accidentRepository.findByLocationAndExpirationTime(
                report.getLocation(), LocalDateTime.now());
        if (desiredReport.isEmpty()) {
            AccidentReport newReport = new AccidentReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(userFactory.findUser(report));
            newReport.setCategory(Accident.fromValue(report.getCategory()));
            accidentRepository.save(newReport);
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
