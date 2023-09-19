package com.hammasir.routingreport.component;

import com.hammasir.routingreport.component.ReportServiceFactory;
import com.hammasir.routingreport.model.DTO.ApprovalDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportHandler {

    private final ReportServiceFactory reportServiceFactory;

    public ReportDTO createReport(ReportDTO report) {
        return reportServiceFactory.getReportService(report.getType()).createReport(report);
    }

    public ReportDTO approveReport(ApprovalDTO approvedReport) {
        return reportServiceFactory.getReportService(approvedReport.getType()).approveReport(approvedReport);
    }

    public List<ReportDTO> getActiveReports(String location) {
        List<ReportDTO> activeReports = new ArrayList<>();
        for (String reportType : reportServiceFactory.getAllReportTypes()) {
            activeReports.addAll(reportServiceFactory.getReportService(reportType).getActiveReports(location));
        }
        return activeReports;
    }
}
