package com.hammasir.routingreport.service;

import com.hammasir.routingreport.model.DTO.ApprovalDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;

import java.util.List;

public interface ReportService {

    ReportDTO createReport(ReportDTO report);
    List<ReportDTO> getActiveReports(String location);
    ReportDTO approveReport(ApprovalDTO approvedReport);
}
