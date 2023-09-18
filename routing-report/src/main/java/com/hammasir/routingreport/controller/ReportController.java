package com.hammasir.routingreport.controller;

import com.hammasir.routingreport.authentication.JwtService;
import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final JwtService jwtService;

    @GetMapping(value = "/active")
    public ResponseEntity<List<ReportDto>> getActive(@RequestBody String location) {
        try {
            List<ReportDto> desiredReport = reportService.getActiveReport(location);
//            if (createdRestaurant == null) {
                return ResponseEntity.badRequest().build();
//            }
//            return ResponseEntity.ok(createdRestaurant);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ReportDto> create(HttpServletRequest request, @RequestBody ReportDto report) {
        try {
            ReportDto createdReport = reportService.createReport(report);
            return ResponseEntity.ok(createdReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
