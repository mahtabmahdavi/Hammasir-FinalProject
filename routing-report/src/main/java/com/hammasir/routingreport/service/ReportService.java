package com.hammasir.routingreport.service;

import com.hammasir.routingreport.component.ApplicationHandler;
import com.hammasir.routingreport.model.dto.ApprovedDTO;
import com.hammasir.routingreport.model.dto.ReportDTO;
import com.hammasir.routingreport.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ApplicationHandler handler;

    public ReportDTO createReport(ReportDTO reportDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reportDto.setUsername(user.getUsername());
        return handler.createReport(reportDto);
    }

//    public List<ReportDTO> getActiveReport(String location) {
//        return handler.getActiveReport(location);
//    }
//
//    public ReportDTO likeReport(long reportId, boolean status) {
//        return handler.likeReport(reportId, status);
//    }

    public ReportDTO approveReport(ApprovedDTO approvedReport) {
        return handler.approveReport(approvedReport);
    }
}
