package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.request.Ride;
import com.mrshaikhmuhammad.ridelink.dto.response.RideMatch;
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

    @PostMapping("save")
    public ResponseEntity<?> saveRides(@RequestBody Ride ride){
        try{
            rideService.saveRide(ride);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search/{radius}")
    public ResponseEntity<?> searchRides(@RequestBody Ride ride, @PathVariable int radius){
        try{
            List<RideMatch> rides = rideService.searchRides(ride, radius);
            return new ResponseEntity<>(rides, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}