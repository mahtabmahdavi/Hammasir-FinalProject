package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.EventReport;
import com.hammasir.routingreport.model.enums.Event;
import com.hammasir.routingreport.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public EventService(EventRepository eventRepository, GeometryFactory geometryFactory,
                        AuthenticationService authService) {
        this.eventRepository = eventRepository;
        this.geometryFactory = geometryFactory;
        this.authService = authService;
    }

    public ReportDto createEventReport(ReportDto report) {
        Optional<EventReport> desiredReport = eventRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            EventReport newReport = new EventReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusDays(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            newReport.setCategory(Event.fromValue(report.getCategory()));
            eventRepository.save(newReport);
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

    public ReportDto getActiveEventReport(ReportDto report) {
        return null;
    }
}
