package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.WeatherReport;
import com.hammasir.routingreport.model.enums.Weather;
import com.hammasir.routingreport.repository.WeatherRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public WeatherService(WeatherRepository weatherRepository, GeometryFactory geometryFactory,
                          UserFactory userFactory) {
        this.weatherRepository = weatherRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createWeatherReport(ReportDto report) {
        Optional<WeatherReport> desiredReport = weatherRepository.findByLocationAndExpirationTime(
                report.getLocation(), LocalDateTime.now());
        if (desiredReport.isEmpty()) {
            WeatherReport newReport = new WeatherReport();
            newReport.setApproved(false);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setDuration(1);
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setType(report.getType());
            newReport.setLocation(geometryFactory.createGeometry(report));
            newReport.setUser(userFactory.findUser(report));
            switch (report.getCategory()) {
                case "FOG" -> newReport.setCategory(Weather.FOG);
                case "CHAINS" -> newReport.setCategory(Weather.CHAINS);
                case "SLIP_ROAD" -> newReport.setCategory(Weather.SLIP_ROAD);
                default -> throw new IllegalArgumentException("Category of weather report is NOT valid!");
            }
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
}
