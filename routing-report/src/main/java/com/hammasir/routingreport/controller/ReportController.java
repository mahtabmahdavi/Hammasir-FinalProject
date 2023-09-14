package com.hammasir.routingreport.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "reports/")
public class ReportController {

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
}
