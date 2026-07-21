package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.request.RideCreateRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.request.RideJoinRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.request.RideSearchRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.response.RideResponseDto;
import com.mrshaikhmuhammad.ridelink.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride")
public class RideController {

    @Autowired
    RideService rideService;

    @PostMapping("create")
    public ResponseEntity<?> createRides(@RequestBody RideCreateRequestDto ride){
        rideService.createRide(ride);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("join")
    public ResponseEntity<?> joinRides(@RequestBody RideJoinRequestDto ride){
        rideService.joinRide(ride);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search/{radius}")
    public ResponseEntity<RideResponseDto> searchRides(@RequestBody RideSearchRequestDto ride, @PathVariable int radius){
        RideResponseDto rides = rideService.searchRides(ride, radius);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }
}