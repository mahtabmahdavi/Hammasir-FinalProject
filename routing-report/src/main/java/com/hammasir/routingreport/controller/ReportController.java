package com.hammasir.routingreport.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.model.dto.ApprovalDTO;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.dto.ReportDTO;
import com.hammasir.routingreport.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = "/active")
    public ResponseEntity<List<ReportDTO>> getActive(@RequestBody String location) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(location);
            List<ReportDTO> reportList = reportService.getActiveReports(jsonNode.get("location").asText());
            return ResponseEntity.ok(reportList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ReportDTO> create(@RequestBody ReportDTO report) {
        try {
            ReportDTO createdReport = reportService.createReport(report);
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
            ReportDTO desiredReport = reportService.approveReport(approvedReport);
            return ResponseEntity.ok(desiredReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
