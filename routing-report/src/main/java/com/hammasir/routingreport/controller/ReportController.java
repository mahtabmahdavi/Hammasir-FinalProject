package com.hammasir.routingreport.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammasir.routingreport.model.DTO.ChangeDTO;
import com.hammasir.routingreport.model.DTO.ReportDTO;
import com.hammasir.routingreport.model.DTO.TimeDTO;
import com.hammasir.routingreport.model.entity.User;
import com.hammasir.routingreport.service.ReportHandler;
import com.hammasir.routingreport.service.report.AccidentReportService;
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
    private final AccidentReportService accidentReportService;

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

    @GetMapping(value = "/most-accidental")
    public ResponseEntity<TimeDTO> getMostAccidental() {
        return ResponseEntity.ok(accidentReportService.getMostAccidentalHour());
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

    @PutMapping(value = "/like")
    public ResponseEntity<ReportDTO> updateLikeCounter(@RequestBody ChangeDTO likeReport) {
        try {
            ReportDTO desiredReport = reportHandler.likeReport(likeReport);
            return ResponseEntity.ok(desiredReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/approve")
    public ResponseEntity<ReportDTO> updateIsApproved(@RequestBody ChangeDTO changedReport) {
        try {
            ReportDTO desiredReport = reportHandler.approveReport(changedReport);
            return ResponseEntity.ok(desiredReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
