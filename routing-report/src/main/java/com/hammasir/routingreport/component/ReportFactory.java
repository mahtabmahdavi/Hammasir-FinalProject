package com.hammasir.routingreport.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.*;
import com.hammasir.routingreport.model.enums.*;
import com.hammasir.routingreport.repository.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReportFactory {

    private final ObjectMapper objectMapper;
    private final AccidentRepository accidentRepository;
    private final BugRepository bugRepository;
    private final BumpRepository bumpRepository;
    private final CameraRepository cameraRepository;
    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;
    private final PoliceRepository policeRepository;
    private final TrafficRepository trafficRepository;
    private final WeatherRepository weatherRepository;
    private final GeometryFactory geometryFactory;
    private final UserFactory userFactory;

    public ReportFactory(ObjectMapper objectMapper, ObjectMapper objectMapper1, AccidentRepository accidentRepository,
                         BugRepository bugRepository, BumpRepository bumpRepository,
                         CameraRepository cameraRepository, EventRepository eventRepository,
                         PlaceRepository placeRepository, PoliceRepository policeRepository,
                         TrafficRepository trafficRepository, WeatherRepository weatherRepository,
                         GeometryFactory geometryFactory, UserFactory userFactory) {
        this.objectMapper = objectMapper;
        this.accidentRepository = accidentRepository;
        this.bugRepository = bugRepository;
        this.bumpRepository = bumpRepository;
        this.cameraRepository = cameraRepository;
        this.eventRepository = eventRepository;
        this.placeRepository = placeRepository;
        this.policeRepository = policeRepository;
        this.trafficRepository = trafficRepository;
        this.weatherRepository = weatherRepository;
        this.geometryFactory = geometryFactory;
        this.userFactory = userFactory;
    }

    public ReportDto createAndSaveReport(ReportDto report) {
        return switch (report.getType()) {
            case "accident" -> objectMapper.convertValue(accidentRepository.save(createAccidentReport(report)), ReportDto.class);
            case "bug" -> objectMapper.convertValue(bugRepository.save(createBugReport(report)), ReportDto.class);
            case "bump" -> objectMapper.convertValue(bumpRepository.save(createBumpReport(report)), ReportDto.class);
            case "camera" -> objectMapper.convertValue(cameraRepository.save(createCameraReport(report)), ReportDto.class);
            case "event" -> objectMapper.convertValue(eventRepository.save(createEventReport(report)), ReportDto.class);
            case "place" -> objectMapper.convertValue(placeRepository.save(createPlaceReport(report)), ReportDto.class);
            case "police" -> objectMapper.convertValue(policeRepository.save(createPoliceReport(report)), ReportDto.class);
            case "traffic" -> objectMapper.convertValue(trafficRepository.save(createTrafficReport(report)), ReportDto.class);
            case "weather" -> objectMapper.convertValue(weatherRepository.save(createWeatherReport(report)), ReportDto.class);
            default -> throw new IllegalArgumentException("Type of the report is NOT valid!");
        };
    }

    private AccidentReport createAccidentReport(ReportDto report) {
        AccidentReport newReport = new AccidentReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(1);
        newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        switch (report.getCategory()) {
            case "LIGHT" -> newReport.setCategory(Accident.LIGHT);
            case "HEAVY" -> newReport.setCategory(Accident.HEAVY);
            case "OPPOSITE_LINE" -> newReport.setCategory(Accident.OPPOSITE_LINE);
            default -> throw new IllegalArgumentException("Category of accident report is NOT valid!");
        }
        return newReport;
    }

    private BugReport createBugReport(ReportDto report) {
        BugReport newReport = new BugReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(1);
        newReport.setExpirationTime(LocalDateTime.now().plusMonths(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        switch (report.getCategory()) {
            case "NO_ENTRY" -> newReport.setCategory(Bug.NO_ENTRY);
            case "DEAD_END" -> newReport.setCategory(Bug.DEAD_END);
            case "DIRT_ROAD" -> newReport.setCategory(Bug.DIRT_ROAD);
            case "NO_CAR_PATH" -> newReport.setCategory(Bug.NO_CAR_PATH);
            case "FLOW_DIRECTION" -> newReport.setCategory(Bug.FLOW_DIRECTION);
            case "OTHER" -> newReport.setCategory(Bug.OTHER);
            default -> throw new IllegalArgumentException("Category of bug report is NOT valid!");
        }
        return newReport;
    }

    private BumpReport createBumpReport(ReportDto report) {
        BumpReport newReport = new BumpReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(1);
        newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        return newReport;
    }

    private CameraReport createCameraReport(ReportDto report) {
        CameraReport newReport = new CameraReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(1);
        newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        switch (report.getCategory()) {
            case "RED_LIGHT" -> newReport.setCategory(Camera.RED_LIGHT);
            case "SPEED_CONTROL" -> newReport.setCategory(Camera.SPEED_CONTROL);
            default -> throw new IllegalArgumentException("Category of camera report is NOT valid!");
        }
        return newReport;
    }

    private EventReport createEventReport(ReportDto report) {
        EventReport newReport = new EventReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(1);
        newReport.setExpirationTime(LocalDateTime.now().plusDays(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        switch (report.getCategory()) {
            case "HOLE" -> newReport.setCategory(Event.HOLE);
            case "BLOCKED_ROAD" -> newReport.setCategory(Event.BLOCKED_ROAD);
            case "CONSTRUCTION_OPERATION" -> newReport.setCategory(Event.CONSTRUCTION_OPERATION);
            default -> throw new IllegalArgumentException("Category of event report is NOT valid!");
        }
        return newReport;
    }

    private PlaceReport createPlaceReport(ReportDto report) {
        PlaceReport newReport = new PlaceReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(1);
        newReport.setExpirationTime(LocalDateTime.now().plusYears(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        switch (report.getCategory()) {
            case "POLICE" -> newReport.setCategory(Place.POLICE);
            case "PARKING" -> newReport.setCategory(Place.PARKING);
            case "GAS_STATION" -> newReport.setCategory(Place.GAS_STATION);
            case "PETROL_STATION" -> newReport.setCategory(Place.PETROL_STATION);
            case "RED_CRESCENT" -> newReport.setCategory(Place.RED_CRESCENT);
            case "WELFARE_SERVICE" -> newReport.setCategory(Place.WELFARE_SERVICE);
            default -> throw new IllegalArgumentException("Category of place report is NOT valid!");
        }
        return newReport;
    }

    private PoliceReport createPoliceReport(ReportDto report) {
        PoliceReport newReport = new PoliceReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(1);
        newReport.setExpirationTime(LocalDateTime.now().plusHours(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        switch (report.getCategory()) {
            case "POLICE" -> newReport.setCategory(Police.POLICE);
            case "SECRET_POLICE" -> newReport.setCategory(Police.SECRET_POLICE);
            case "OPPOSITE_LINE" -> newReport.setCategory(Police.OPPOSITE_LINE);
            default -> throw new IllegalArgumentException("Category of police report is NOT valid!");
        }
        return newReport;
    }

    private TrafficReport createTrafficReport(ReportDto report) {
        TrafficReport newReport = new TrafficReport();
        newReport.setApproved(false);
        newReport.setCreationTime(LocalDateTime.now());
        newReport.setDuration(10);
        newReport.setExpirationTime(LocalDateTime.now().plusMinutes(newReport.getDuration()));
        newReport.setType(report.getType());
        newReport.setLocation(geometryFactory.createGeometry(report));
        newReport.setUser(userFactory.findUser(report));
        switch (report.getCategory()) {
            case "JAM" -> newReport.setCategory(Traffic.JAM);
            case "SMOOTH" -> newReport.setCategory(Traffic.SMOOTH);
            case "SEMI_HEAVY" -> newReport.setCategory(Traffic.SEMI_HEAVY);
            default -> throw new IllegalArgumentException("Category of traffic report is NOT valid!");
        }
        return newReport;
    }

    private WeatherReport createWeatherReport(ReportDto report) {
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
        return newReport;
    }
}
