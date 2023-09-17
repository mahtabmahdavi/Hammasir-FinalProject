package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.GeometryFactory;
import com.hammasir.routingreport.component.ReportFactory;
import com.hammasir.routingreport.component.UserFactory;
import com.hammasir.routingreport.model.dto.ReportDto;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportFactory reportFactory;

    public ReportService(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    public ReportDto createReport(ReportDto reportDto) {
        return reportFactory.createAndSaveReport(reportDto);
    }

}
