package com.hammasir.routingreport.controller;

import com.hammasir.routingreport.model.dto.ReportDto;
import com.hammasir.routingreport.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


//    @GetMapping
//    public ResponseEntity<User> create(@RequestBody RestaurantDTO restaurant) {
//        try {
//            Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
//            if (createdRestaurant == null) {
//                return ResponseEntity.badRequest().build();
//            }
//            return ResponseEntity.ok(createdRestaurant);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @PostMapping(value = "create")
    public ResponseEntity<ReportDto> create(@RequestBody ReportDto report) {
        return ResponseEntity.ok(reportService.createReport(report));
    }
}
