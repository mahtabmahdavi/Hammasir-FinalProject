package com.hammasir.routingreport.controller;

import com.hammasir.routingreport.model.dto.ApprovedDTO;
import com.hammasir.routingreport.model.dto.ReportDTO;
import com.hammasir.routingreport.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

//    @GetMapping(value = "/active")
//    public ResponseEntity<List<ReportDto>> getActive(@RequestBody String location) {
//        try {
//            List<ReportDto> reportList = reportService.getActiveReport(location);
//            return ResponseEntity.ok(reportList);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @PostMapping(value = "/create")
    public ResponseEntity<ReportDTO> create(HttpServletRequest request, @RequestBody ReportDTO report) {
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
    public ResponseEntity<ReportDTO> updateIsApproved(@RequestBody ApprovedDTO approvedReport) {
        try {
            ReportDTO desiredReport = reportService.approveReport(approvedReport);
            return ResponseEntity.ok(desiredReport);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
