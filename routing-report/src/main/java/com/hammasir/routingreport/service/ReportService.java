package com.hammasir.routingreport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.component.ApplicationHandler;
import com.hammasir.routingreport.model.dto.ApprovalDTO;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ApplicationHandler handler;

    public CreationDTO createReport(CreationDTO creationDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        creationDto.setUsername(user.getUsername());
        return handler.createReport(creationDto);
    }

//    public List<ReportDTO> getActiveReport(String location) {
//        return handler.getActiveReport(location);
//    }
//
//    public ReportDTO likeReport(long reportId, boolean status) {
//        return handler.likeReport(reportId, status);
//    }

    public CreationDTO approveReport(ApprovalDTO approvedReport) {
        return handler.approveReport(approvedReport);
    }

    public List<ReportDto> getActiveReports(String location) {
        return handler.getActiveReports(location);
    }
}
