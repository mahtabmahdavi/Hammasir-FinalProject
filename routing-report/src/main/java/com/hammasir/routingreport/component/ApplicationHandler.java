package com.hammasir.routingreport.component;

import com.hammasir.routingreport.model.dto.ApprovalDTO;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.dto.ReportDTO;
import com.hammasir.routingreport.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationHandler {

    private final AccidentService accidentService;
    private final BugService bugService;
    private final BumpService bumpService;
    private final CameraService cameraService;
    private final EventService eventService;
    private final PlaceService placeService;
    private final PoliceService policeService;
    private final TrafficService trafficService;
    private final WeatherService weatherService;

    public ReportDTO createReport(ReportDTO report) {
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

//    public ReportDTO likeReport(long reportId, boolean status) {
//
//    }

    public ReportDTO approveReport(ApprovalDTO approvedReport) {
        return switch (approvedReport.getType()) {
            case "bug" -> bugService.approveBugReport(approvedReport);
            case "bump" -> bumpService.approveBumpReport(approvedReport);
            case "camera" -> cameraService.approveCameraReport(approvedReport);
            case "place" -> placeService.approvePlaceReport(approvedReport);
            default -> throw new IllegalArgumentException("Type of the report is NOT valid!");
        };
    }

    public List<ReportDTO> getActiveReports(String location) {
        List<ReportDTO> activeReports = new ArrayList<>();
        activeReports.addAll(accidentService.getActiveAccidentReport(location));
        activeReports.addAll(bugService.getActiveBugReport(location));
        activeReports.addAll(bumpService.getActiveBumpReport(location));
        activeReports.addAll(cameraService.getActiveCameraReport(location));
        activeReports.addAll(eventService.getActiveEventReport(location));
        activeReports.addAll(placeService.getActivePlaceReport(location));
        activeReports.addAll(policeService.getActivePoliceReport(location));
        activeReports.addAll(trafficService.getActiveTrafficReport(location));
        activeReports.addAll(weatherService.getActiveWeatherReport(location));
        return activeReports;
    }
}
