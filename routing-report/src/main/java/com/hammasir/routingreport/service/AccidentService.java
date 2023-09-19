package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.AccidentReport;
import com.hammasir.routingreport.model.enums.Accident;
import com.hammasir.routingreport.repository.AccidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public CreationDTO convertToReportDto(AccidentReport report) {
        return CreationDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public CreationDTO createAccidentReport(CreationDTO report) {
        boolean isExisted = accidentRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            AccidentReport newReport =  new AccidentReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Accident.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(accidentRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }



    public List<ReportDto> getActiveAccidentReport(String location) {
        List<AccidentReport> activeReports = accidentRepository.findByIsApprovedAndLocation(geometryFactory.createGeometry(location));
        List<ReportDto> arr = new ArrayList<>();
        for (AccidentReport act : activeReports) {
            ReportDto r = new ReportDto();
            r.setType(act.getType());
            r.setCategory(act.getCategory().name());
            r.setLocation(geometryFactory.createWkt(act.getLocation()));
            r.setLike(act.getLikeCounter());
            r.setUsername(act.getUser().getUsername());
            arr.add(r);
        }
        return arr;
    }
}
