package com.hammasir.routingreport.service;

import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.*;
import com.hammasir.routingreport.model.enums.*;
import com.hammasir.routingreport.repository.UserRepository;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReportService {

    private final UserRepository userRepository;

    public ReportService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createReport(ReportDto reportDto) {
        Report report = determineReportType(reportDto);
        report.setApproved(false);
        report.setLocation(convertToGeometry(reportDto));
        report.setUser(findUserOfReport(reportDto));
        saveReport(report);
    }

    private Report determineReportType(ReportDto reportDto) {
        String condition = reportDto.getType();
        switch (condition) {
            case "accident" -> {
                return convertToAccidentReport(reportDto);
            }
            case "bug" -> {
                return convertTBugReport(reportDto);
            }
            case "bump" -> {
                return convertToBumpReport(reportDto);
            }
            case "camera" -> {
                return converToCameraReport(reportDto);
            }
            case "event" -> {
                return convertToEventReport(reportDto);
            }
            case "place" -> {
                return convertToPlaceReport(reportDto);
            }
            case "police" -> {
                return converToPoliceReport(reportDto);
            }
            case "traffic" -> {
                return convertToTrafficReport(reportDto);
            }
            case "weather" -> {
                return convertToWeather(reportDto);
            }
            default -> throw new IllegalArgumentException("Type of the report is NOT valid!");
        }
    }

    private AccidentReport convertToAccidentReport(ReportDto report) {
        AccidentReport newObj = new AccidentReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusHours(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "LIGHT" -> newObj.setCategory(Accident.LIGHT);
            case "HEAVY" -> newObj.setCategory(Accident.HEAVY);
            case "OPPOSITE_LINE" -> newObj.setCategory(Accident.OPPOSITE_LINE);
            default -> throw new IllegalArgumentException("Category of accident report is NOT valid!");
        }
        return newObj;
    }

    private BugReport convertTBugReport(ReportDto report) {
        BugReport newObj = new BugReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusMonths(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "NO_ENTRY" -> newObj.setCategory(Bug.NO_ENTRY);
            case "DEAD_END" -> newObj.setCategory(Bug.DEAD_END);
            case "DIRT_ROAD" -> newObj.setCategory(Bug.DIRT_ROAD);
            case "NO_CAR_PATH" -> newObj.setCategory(Bug.NO_CAR_PATH);
            case "FLOW_DIRECTION" -> newObj.setCategory(Bug.FLOW_DIRECTION);
            case "OTHER" -> newObj.setCategory(Bug.OTHER);
            default -> throw new IllegalArgumentException("Category of bug report is NOT valid!");
        }
        return newObj;
    }

    private BumpReport convertToBumpReport(ReportDto report) {
        BumpReport newObj = new BumpReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusYears(newObj.getDuration()));
        newObj.setType(report.getType());
        return newObj;
    }

    private CameraReport converToCameraReport(ReportDto report) {
        CameraReport newObj = new CameraReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusYears(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "RED_LIGHT" -> newObj.setCategory(Camera.RED_LIGHT);
            case "SPEED_CONTROL" -> newObj.setCategory(Camera.SPEED_CONTROL);
            default -> throw new IllegalArgumentException("Category of camera report is NOT valid!");
        }
        return newObj;
    }

    private EventReport convertToEventReport(ReportDto report) {
        EventReport newObj = new EventReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusDays(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "HOLE" -> newObj.setCategory(Event.HOLE);
            case "BLOCKED_ROAD" -> newObj.setCategory(Event.BLOCKED_ROAD);
            case "CONSTRUCTION_OPERATION" -> newObj.setCategory(Event.CONSTRUCTION_OPERATION);
            default -> throw new IllegalArgumentException("Category of event report is NOT valid!");
        }
        return newObj;
    }

    private PlaceReport convertToPlaceReport(ReportDto report) {
        PlaceReport newObj = new PlaceReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusYears(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "POLICE" -> newObj.setCategory(Place.POLICE);
            case "PARKING" -> newObj.setCategory(Place.PARKING);
            case "GAS_STATION" -> newObj.setCategory(Place.GAS_STATION);
            case "PETROL_STATION" -> newObj.setCategory(Place.PETROL_STATION);
            case "RED_CRESCENT" -> newObj.setCategory(Place.RED_CRESCENT);
            case "WELFARE_SERVICE" -> newObj.setCategory(Place.WELFARE_SERVICE);
            default -> throw new IllegalArgumentException("Category of place report is NOT valid!");
        }
        return newObj;
    }

    private PoliceReport converToPoliceReport(ReportDto report) {
        PoliceReport newObj = new PoliceReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusHours(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "POLICE" -> newObj.setCategory(Police.POLICE);
            case "SECRET_POLICE" -> newObj.setCategory(Police.SECRET_POLICE);
            case "OPPOSITE_LINE" -> newObj.setCategory(Police.OPPOSITE_LINE);
            default -> throw new IllegalArgumentException("Category of police report is NOT valid!");
        }
        return newObj;
    }

    private TrafficReport convertToTrafficReport(ReportDto report) {
        TrafficReport newObj = new TrafficReport();
        newObj.setDuration(10);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusMinutes(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "JAM" -> newObj.setCategory(Traffic.JAM);
            case "SMOOTH" -> newObj.setCategory(Traffic.SMOOTH);
            case "SEMI_HEAVY" -> newObj.setCategory(Traffic.SEMI_HEAVY);
            default -> throw new IllegalArgumentException("Category of traffic report is NOT valid!");
        }
        return newObj;
    }

    private WeatherReport convertToWeather(ReportDto report) {
        WeatherReport newObj = new WeatherReport();
        newObj.setDuration(1);
        newObj.setCreationTime(LocalDateTime.now());
        newObj.setExpirationTime(LocalDateTime.now().plusHours(newObj.getDuration()));
        newObj.setType(report.getType());
        String condition = report.getCategory();
        switch (condition) {
            case "FOG" -> newObj.setCategory(Weather.FOG);
            case "CHAINS" -> newObj.setCategory(Weather.CHAINS);
            case "SLIP_ROAD" -> newObj.setCategory(Weather.SLIP_ROAD);
            default -> throw new IllegalArgumentException("Category of weather report is NOT valid!");
        }
        return newObj;
    }

    private Geometry convertToGeometry(ReportDto report) {
        String location = report.getLocation();
        WKTReader reader = new WKTReader();
        try {
            return reader.read(location);
        } catch (Exception e) {
            throw new IllegalArgumentException("Location is NOT valid!");
        }
    }

    private User findUserOfReport(ReportDto report) {
        Optional<User> user = userRepository.findByUsername(report.getUsername());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("User is NOT found!");
        }
    }

    private void saveReport(Report reportDto) {
        String condition = reportDto.getType();
        switch (condition) {
            case "accident" -> {
                return convertToAccidentReport(reportDto);
            }
            case "bug" -> {
                return convertTBugReport(reportDto);
            }
            case "camera" -> {
                return converToCameraReport(reportDto);
            }
            case "event" -> {
                return convertToEventReport(reportDto);
            }
            case "location" -> {
                return convertToPlaceReport(reportDto);
            }
            case "police" -> {
                return converToPoliceReport(reportDto);
            }
            case "speed-bump" -> {
                return convertToBumpReport(reportDto);
            }
            case "traffic" -> {
                return convertToTrafficReport(reportDto);
            }
            default -> throw new IllegalArgumentException("Type of the report is NOT valid!");
        }
    }
}
