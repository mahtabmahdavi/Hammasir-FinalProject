package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.PoliceReport;
import com.hammasir.routingreport.model.enums.Police;
import com.hammasir.routingreport.repository.PoliceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PoliceService {

    private final PoliceRepository policeRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;
    private final ObjectMapper objectMapper;

    public ReportDto createPoliceReport(ReportDto report) {
        boolean isExisted = policeRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            PoliceReport newReport = new PoliceReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setCategory(Police.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            policeRepository.save(newReport);
            return objectMapper.convertValue(newReport, ReportDto.class);
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActivePoliceReport(ReportDto report) {
        return null;
    }
}
