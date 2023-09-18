package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.WeatherReport;
import com.hammasir.routingreport.model.enums.Weather;
import com.hammasir.routingreport.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final GeometryFactory geometryFactory;
    private final AuthenticationService authService;

    public WeatherService(WeatherRepository weatherRepository, GeometryFactory geometryFactory,
                          AuthenticationService authService) {
        this.weatherRepository = weatherRepository;
        this.geometryFactory = geometryFactory;
        this.authService = authService;
    }

    public ReportDto createWeatherReport(ReportDto report) {
        Optional<WeatherReport> desiredReport = weatherRepository.findByLocationAndExpirationTime(
                report.getLocation());
        if (desiredReport.isEmpty()) {
            WeatherReport newReport = new WeatherReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(authService.findUser(report));
            newReport.setCategory(Weather.fromValue(report.getCategory()));
            weatherRepository.save(newReport);
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

    public ReportDto getActiveWeatherReport(ReportDto report) {
        return null;
    }
}
