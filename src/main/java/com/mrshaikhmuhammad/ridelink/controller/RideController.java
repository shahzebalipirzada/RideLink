package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.request.RideRequestDto;
import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.dto.response.RideResponseDto;
import com.mrshaikhmuhammad.ridelink.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
public class RideController {

    @Autowired
    RideService rideService;

    @PostMapping("create")
    public ResponseEntity<?> createRides(@RequestBody RideRequestDto ride){
        rideService.createRide(ride);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/search/{radius}")
    public ResponseEntity<RideResponseDto> searchRides(@RequestBody RideRequestDto ride, @PathVariable int radius){
        RideResponseDto rides = rideService.searchRides(ride, radius);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }
}