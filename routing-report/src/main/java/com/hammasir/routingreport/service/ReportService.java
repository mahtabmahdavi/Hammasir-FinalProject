package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.ApplicationHandler;
import com.hammasir.routingreport.model.dto.ReportDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ApplicationHandler handler;

    public ReportService(ApplicationHandler handler) {
        this.handler = handler;
    }

    public ReportDto createReport(ReportDto reportDto) {
        return handler.createReport(reportDto);
    }

    public List<ReportDto> getActiveReport(String location) {
        return handler.getActiveReport(location);
    }
}
