package com.hammasir.routingreport.service;

import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.*;
import com.hammasir.routingreport.model.enums.*;
import com.hammasir.routingreport.repository.ReportRepository;
import com.hammasir.routingreport.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public void createReport(ReportDto reportDto) {

        Report report;
        String condition = reportDto.getType();
        switch (condition) {
            case "accident" -> report = convertToAccidentReport(reportDto);
            case "bug" -> report = convertToMapBugReport(reportDto);
            case "camera" -> report = converToCameraReport(reportDto);
            case "event" -> report = convertToEventWayReport(reportDto);
            case "location" -> report = convertToRoadLocationReport(reportDto);
            case "police" -> report = converToPoliceReport(reportDto);
            case "speed-bump" -> report =  convertToSpeedBumpReport();
            case "traffic" -> report = convertToTrafficReport(reportDto);
            default -> throw new IllegalArgumentException("Type of the report is NOT valid!");
        }
        report.setApproved(false);
        report.setCreationTime(LocalDateTime.now());
        report.setExpirationTime(LocalDateTime.now().plusMinutes(report.getDuration()));

        Optional<User> user = userRepository.findByUsername(reportDto.getUsername());
        if (user.isPresent()) {
            User desiredUser = user.get();
            report.setUser(desiredUser);
        } else {
            throw new IllegalArgumentException("User is NOT valid!");
        }

        return reportRepository.save(report);
    }









    private AccidentReport convertToAccidentReport(ReportDto report) {
        AccidentReport newObj = new AccidentReport();
        String condition = report.getCategory();
        switch (condition) {
            case "LIGHT" -> newObj.setAccidentCategory(Accident.LIGHT);
            case "HEAVY" -> newObj.setAccidentCategory(Accident.HEAVY);
            case "OPPOSITE_LINE" -> newObj.setAccidentCategory(Accident.OPPOSITE_LINE);
            default -> throw new IllegalArgumentException("Category of accident report is NOT valid!");
        }
        return newObj;
    }

    private MapBugReport convertToMapBugReport(ReportDto report) {
        MapBugReport newObj = new MapBugReport();
        String condition = report.getCategory();
        switch (condition) {
            case "NO_ENTRY" -> newObj.setMapBugCategory(MapBug.NO_ENTRY);
            case "DEAD_END" -> newObj.setMapBugCategory(MapBug.DEAD_END);
            case "DIRT_ROAD" -> newObj.setMapBugCategory(MapBug.DIRT_ROAD);
            case "NO_CAR_PATH" -> newObj.setMapBugCategory(MapBug.NO_CAR_PATH);
            case "FLOW_DIRECTION" -> newObj.setMapBugCategory(MapBug.FLOW_DIRECTION);
            case "OTHER" -> newObj.setMapBugCategory(MapBug.OTHER);
            default -> throw new IllegalArgumentException("Category of map bug report is NOT valid!");
        }
        return newObj;
    }

    private CameraReport converToCameraReport(ReportDto report) {
        CameraReport newObj = new CameraReport();
        String condition = report.getCategory();
        switch (condition) {
            case "RED_LIGHT" -> newObj.setCameraCategory(Camera.RED_LIGHT);
            case "SPEED_CONTROL" -> newObj.setCameraCategory(Camera.SPEED_CONTROL);
            default -> throw new IllegalArgumentException("Category of camera report is NOT valid!");
        }
        return newObj;
    }

    private EventWayReport convertToEventWayReport(ReportDto report) {
        EventWayReport newObj = new EventWayReport();
        String condition = report.getCategory();
        switch (condition) {
            case "HOLE" -> newObj.setEventWayCategory(EventWay.HOLE);
            case "BLOCKED_ROAD" -> newObj.setEventWayCategory(EventWay.BLOCKED_ROAD);
            case "CONSTRUCTION_OPERATION" -> newObj.setEventWayCategory(EventWay.CONSTRUCTION_OPERATION);
            default -> throw new IllegalArgumentException("Category of event way report is NOT valid!");
        }
        return newObj;
    }

    private RoadLocationReport convertToRoadLocationReport(ReportDto report) {
        RoadLocationReport newObj = new RoadLocationReport();
        String condition = report.getCategory();
        switch (condition) {
            case "POLICE" -> newObj.setRoadLocationCategory(RoadLocation.POLICE);
            case "PARKING" -> newObj.setRoadLocationCategory(RoadLocation.PARKING);
            case "GAS_STATION" -> newObj.setRoadLocationCategory(RoadLocation.GAS_STATION);
            case "PETROL_STATION" -> newObj.setRoadLocationCategory(RoadLocation.PETROL_STATION);
            case "RED_CRESCENT" -> newObj.setRoadLocationCategory(RoadLocation.RED_CRESCENT);
            case "WELFARE_SERVICE" -> newObj.setRoadLocationCategory(RoadLocation.WELFARE_SERVICE);
            default -> throw new IllegalArgumentException("Category of road location report is NOT valid!");
        }
        return newObj;
    }

    private PoliceReport converToPoliceReport(ReportDto report) {
        PoliceReport newObj = new PoliceReport();
        String condition = report.getCategory();
        switch (condition) {
            case "POLICE" -> newObj.setPoliceCategory(Police.POLICE);
            case "SECRET_POLICE" -> newObj.setPoliceCategory(Police.SECRET_POLICE);
            case "OPPOSITE_LINE" -> newObj.setPoliceCategory(Police.OPPOSITE_LINE);
            default -> throw new IllegalArgumentException("Category of police report is NOT valid!");
        }
        return newObj;
    }

    private SpeedBumpReport convertToSpeedBumpReport() {
        return new SpeedBumpReport();
    }

    private TrafficReport convertToTrafficReport(ReportDto report) {
        TrafficReport newObj = new TrafficReport();
        String condition = report.getCategory();
        switch (condition) {
            case "JAM" -> newObj.setTrafficCategory(Traffic.JAM);
            case "SMOOTH" -> newObj.setTrafficCategory(Traffic.SMOOTH);
            case "SEMI_HEAVY" -> newObj.setTrafficCategory(Traffic.SEMI_HEAVY);
            default -> throw new IllegalArgumentException("Category of traffic report is NOT valid!");
        }
        return newObj;
    }
}
