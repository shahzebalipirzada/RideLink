package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class RideService {

    @Autowired
    RideRepository rideRepository;

    public void saveRide(Ride ride){
        rideRepository.save(ride);
    }

    public List<Ride> searchRide(Ride ride){
        List<Ride> rides =  rideRepository.findAll();
        List<Ride> filteredRides = new ArrayList<>();
        for (Ride x : rides) {
            if (
                    ride.getSource().equals(x.getSource()) &&
                    ride.getDestination().equals(x.getDestination()) &&
                    ride.getDepartureTime().equals(x.getDepartureTime())
                ) {
                filteredRides.add(x);
            }
        }
        return filteredRides;
    }

}
