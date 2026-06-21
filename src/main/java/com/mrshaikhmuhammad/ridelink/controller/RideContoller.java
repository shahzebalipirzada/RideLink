package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ride")
public class RideContoller {

    @Autowired
    RideService rideService;

    @PostMapping
    public ResponseEntity<?> saveRides(@RequestBody Ride ride){
        try{
            rideService.saveRide(ride);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{radius}")
    public ResponseEntity<?> searchRides(){
        return null;
    }
}