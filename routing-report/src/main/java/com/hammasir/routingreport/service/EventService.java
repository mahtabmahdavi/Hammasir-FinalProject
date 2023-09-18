package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.EventReport;
import com.hammasir.routingreport.model.enums.Event;
import com.hammasir.routingreport.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;
    private final ObjectMapper objectMapper;

    public ReportDto createEventReport(ReportDto report) {
        boolean isExisted = eventRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            EventReport newReport = new EventReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setCategory(Event.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            eventRepository.save(newReport);
            return objectMapper.convertValue(newReport, ReportDto.class);
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public ReportDto getActiveEventReport(ReportDto report) {
        return null;
    }
}
