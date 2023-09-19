package com.hammasir.routingreport.component;

import com.hammasir.routingreport.service.*;
import com.hammasir.routingreport.service.report.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReportServiceFactory {
    private final Map<String, ReportService> reportServiceMap = new HashMap<>();

    @Autowired
    public ReportServiceFactory(
            AccidentReportService accidentReportService,
            BugReportService bugReportService,
            BumpReportService bumpReportService,
            CameraReportService cameraReportService,
            EventReportService eventReportService,
            PlaceReportService placeReportService,
            PoliceReportService policeReportService,
            TrafficReportService trafficReportService,
            WeatherReportService weatherReportService
    ) {
        reportServiceMap.put("accident", accidentReportService);
        reportServiceMap.put("bug", bugReportService);
        reportServiceMap.put("bump", bumpReportService);
        reportServiceMap.put("camera", cameraReportService);
        reportServiceMap.put("event", eventReportService);
        reportServiceMap.put("place", placeReportService);
        reportServiceMap.put("police", policeReportService);
        reportServiceMap.put("traffic", trafficReportService);
        reportServiceMap.put("weather", weatherReportService);
    }

    public ReportService getReportService(String reportType) {
        ReportService reportService = reportServiceMap.get(reportType);
        if (reportService == null) {
            throw new IllegalArgumentException("Invalid report type: " + reportType);
        }
        return reportService;
    }

    public Iterable<String> getAllReportTypes() {
        return reportServiceMap.keySet();
    }
}
