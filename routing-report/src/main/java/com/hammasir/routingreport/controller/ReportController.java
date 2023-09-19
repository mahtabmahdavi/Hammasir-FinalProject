package com.hammasir.routingreport.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.model.dto.ApprovalDTO;
import com.hammasir.routingreport.model.dto.CreationDTO;
import com.hammasir.routingreport.model.dto.ReportDto;
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
    public ResponseEntity<List<ReportDto>> getActive(@RequestBody String location) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(location);
            List<ReportDto> reportList = reportService.getActiveReports(jsonNode.get("location").asText());
            return ResponseEntity.ok(reportList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CreationDTO> create(@RequestBody CreationDTO report) {
        try {
            CreationDTO createdReport = reportService.createReport(report);
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
    public ResponseEntity<CreationDTO> updateIsApproved(@RequestBody ApprovalDTO approvedReport) {
        try {
            CreationDTO desiredReport = reportService.approveReport(approvedReport);
            return ResponseEntity.ok(desiredReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
