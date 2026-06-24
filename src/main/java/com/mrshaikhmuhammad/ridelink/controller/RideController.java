package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ride")
public class RideController {

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
    public ResponseEntity<?> searchRides(@RequestBody Ride ride, @PathVariable int radius){
        try{
            List<Ride> rides = rideService.searchRides(ride, radius);
            return new ResponseEntity<>(rides, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}