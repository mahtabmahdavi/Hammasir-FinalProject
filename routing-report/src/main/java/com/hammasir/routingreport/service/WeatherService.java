package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.entity.WeatherReport;
import com.hammasir.routingreport.model.enums.Weather;
import com.hammasir.routingreport.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final AuthenticationService authenticationService;
    private final GeometryFactory geometryFactory;

    public CreationDTO convertToReportDto(WeatherReport report) {
        return CreationDTO.builder()
                .type(report.getType())
                .category(report.getCategory().name())
                .location(geometryFactory.createWkt(report.getLocation()))
                .username(report.getUser().getUsername())
                .build();
    }

    public CreationDTO createWeatherReport(CreationDTO report) {
        boolean isExisted = weatherRepository.existsByLocationAndExpirationTime(report.getLocation());
        if (!isExisted) {
            WeatherReport newReport = new WeatherReport();
            newReport.setType(report.getType());
            newReport.setIsApproved(true);
            newReport.setLikeCounter(0);
            newReport.setDuration(1);
            newReport.setCreationTime(LocalDateTime.now());
            newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
            newReport.setLocation(geometryFactory.createGeometry(report.getLocation()));
            newReport.setCategory(Weather.fromValue(report.getCategory()));
            newReport.setContributors(List.of());
            newReport.setUser(authenticationService.findUser(report.getUsername()));
            return convertToReportDto(weatherRepository.save(newReport));
        } else {
            throw new IllegalArgumentException("This report is already existed!");
        }
    }

    public List<CreationDTO> getActiveWeatherReport(String location) {
         List<WeatherReport> arr = weatherRepository.findActive(geometryFactory.createGeometry(location));
         return arr.stream()
                 .map(this::convertToReportDto)
                 .collect(Collectors.toList());
    }
}
