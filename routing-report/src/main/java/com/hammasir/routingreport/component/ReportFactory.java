package com.hammasir.routingreport.component;

import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.service.*;
import org.springframework.stereotype.Component;

@Component
public class ReportFactory {

    private final AccidentService accidentService;
    private final BugService bugService;
    private final BumpService bumpService;
    private final CameraService cameraService;
    private final EventService eventService;
    private final PlaceService placeService;
    private final PoliceService policeService;
    private final TrafficService trafficService;
    private final WeatherService weatherService;

    public ReportFactory(AccidentService accidentService, BugService bugService, BumpService bumpService,
                         CameraService cameraService, EventService eventService, PlaceService placeService,
                         PoliceService policeService, TrafficService trafficService, WeatherService weatherService) {
        this.accidentService = accidentService;
        this.bugService = bugService;
        this.bumpService = bumpService;
        this.cameraService = cameraService;
        this.eventService = eventService;
        this.placeService = placeService;
        this.policeService = policeService;
        this.trafficService = trafficService;
        this.weatherService = weatherService;
    }

    public ReportDto createReport(ReportDto report) {
        return switch (report.getType()) {
            case "accident" -> accidentService.createAccidentReport(report);
            case "bug" -> bugService.createBugReport(report);
            case "bump" -> bumpService.createBumpReport(report);
            case "camera" -> cameraService.createCameraReport(report);
            case "event" -> eventService.createEventReport(report);
            case "place" -> placeService.createPlaceReport(report);
            case "police" -> policeService.createPoliceReport(report);
            case "traffic" -> trafficService.createTrafficReport(report);
            case "weather" -> weatherService.createWeatherReport(report);
            default -> throw new IllegalArgumentException("Type of the report is NOT valid!");
        };
    }
}
