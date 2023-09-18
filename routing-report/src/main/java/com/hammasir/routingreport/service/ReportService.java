package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.ApplicationHandler;
import com.hammasir.routingreport.model.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ApplicationHandler handler;

    public ReportDto createReport(ReportDto reportDto) {
        return handler.createReport(reportDto);
    }

    public List<ReportDto> getActiveReport(String location) {
        return handler.getActiveReport(location);
    }
}
