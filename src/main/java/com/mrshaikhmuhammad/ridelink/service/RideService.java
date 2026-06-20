package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.entity.GeoPoint;
import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.entity.Path;
import com.mrshaikhmuhammad.ridelink.external.osrm.route.OsrmRouteClient;
import com.mrshaikhmuhammad.ridelink.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideService {

    @Autowired
    RideRepository rideRepository;
    @Autowired
    OsrmRouteClient osrmClient;

    public void saveRide(Ride ride){
        GeoPoint origin = ride.getOrigin();
        GeoPoint destination = ride.getDestination();
        Path routes = osrmClient.getRoute(origin, destination);
        ride.setPath(routes);

        rideRepository.save(ride);
    }

    public List<Ride> searchRide(Ride ride){
//        List<Ride> rides =  rideRepository.findAll();
//        List<Ride> filteredRides = new ArrayList<>();
//        for (Ride x : rides) {
//            if (
//                    ride.getOrigin().equals(x.getOrigin()) &&
//                    ride.getDestination().equals(x.getDestination()) &&
//                    ride.getDepartureTime().equals(x.getDepartureTime())
//                ) {
//                filteredRides.add(x);
//            }
//        }
        return null;
    }

}
