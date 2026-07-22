package com.mrshaikhmuhammad.ridelink.controller;

import lombok.*;
import com.mrshaikhmuhammad.ridelink.service.*;
import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.dto.response.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;


@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("create")
    public ResponseEntity<Void> createRides(@RequestBody RideCreateRequestDto ride){
        rideService.createRide(ride);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("join")
    public ResponseEntity<Void> joinRides(@RequestBody RideJoinRequestDto ride){
        rideService.joinRide(ride);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search/{radius}")
    public ResponseEntity<RideResponseDto> searchRides(@RequestBody RideSearchRequestDto ride, @PathVariable int radius){
        RideResponseDto rides = rideService.searchRides(ride, radius);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }
}