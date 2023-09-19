package com.hammasir.routingreport.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.model.DTO.ApprovalDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.component.ReportHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportHandler reportHandler;

    @GetMapping(value = "/active")
    public ResponseEntity<List<ReportDTO>> getActive(@RequestBody String location) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(location);
            List<ReportDTO> reportList = reportHandler.getActiveReports(jsonNode.get("location").asText());
            return ResponseEntity.ok(reportList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ReportDTO> create(@RequestBody ReportDTO report) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            report.setUsername(user.getUsername());
            ReportDTO createdReport = reportHandler.createReport(report);
            return ResponseEntity.ok(createdReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



//    @PutMapping(value = "/like/{id}")
//    public ResponseEntity<ReportDto> like(@PathVariable("id") long reportId, @RequestBody String like) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(like);
//            boolean status = Boolean.parseBoolean(jsonNode.get("like").asText());
//            ReportDto createdReport = reportService.likeReport(reportId, status);
//            return ResponseEntity.ok(createdReport);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @PutMapping(value = "/approve")
    public ResponseEntity<ReportDTO> updateIsApproved(@RequestBody ApprovalDTO approvedReport) {
        try {
            ReportDTO desiredReport = reportHandler.approveReport(approvedReport);
            return ResponseEntity.ok(desiredReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
