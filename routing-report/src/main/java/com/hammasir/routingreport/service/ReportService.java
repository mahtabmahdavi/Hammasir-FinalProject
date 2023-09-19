package com.hammasir.routingreport.service;

import com.hammasir.routingreport.model.DTO.ChangeDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;

import java.util.List;

public interface ReportService {

    ReportDTO createReport(ReportDTO report);
    List<ReportDTO> getActiveReports(String location);
    ReportDTO likeReport(ChangeDTO likedReport);
    ReportDTO approveReport(ChangeDTO changedReport);
}
