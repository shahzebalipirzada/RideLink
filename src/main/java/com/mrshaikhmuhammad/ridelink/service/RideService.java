package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.external.osrm.route.OsrmRouteClient;
import com.mrshaikhmuhammad.ridelink.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RideService {

    @Autowired
    RideRepository rideRepository;
    @Autowired
    OsrmRouteClient osrmClient;

    public void saveRide(Ride ride){
        GeoPoint origin = ride.getOrigin();
        GeoPoint destination = ride.getDestination();
        Path routes = osrmClient.getTrip(
                List.of(origin, destination)
        );
        ride.setPath(routes);
        rideRepository.save(ride);
    }

    public List<Ride> searchRide(){
        return null;
    }

}
