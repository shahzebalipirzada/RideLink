package com.mrshaikhmuhammad.ridelink.service.scoring;

import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmRouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class RideScore {

    @Autowired
    OsrmRouteClient routeClient;

    private static final double WEIGHT_TIME     = 0.40;
    private static final double WEIGHT_DETOUR   = 0.45;
    private static final double WEIGHT_COVERAGE = 0.15;
    private static final double MAX_WAIT_TIME_MINUTES = 120;

    public double score(Ride requestingRide, Ride candidate) {

        Ride driver, passenger;
        if(requestingRide.getRole() == Role.PASSENGER){
            driver = candidate;
            passenger = requestingRide;
        }
        else{
            driver = requestingRide;
            passenger = candidate;
        }

        if (driver.getPath().routes().get(0) == null || passenger.getPath().routes().get(0) == null)
            return 0.0;

        double driverDistance = driver.getPath().routes().get(0).distance();
        double passengerDistance = passenger.getPath().routes().get(0).distance();
        Path.Route sharedRoute = routeClient.getRoute(
            List.of(
                    driver.getOrigin(),
                    passenger.getOrigin(),
                    passenger.getDestination(),
                    driver.getDestination()
            )
        ).routes().get(0);

        Instant passengerDepartureTime = passenger.getDepartureTime();
        Instant driverDepartureTime = driver.getDepartureTime().plusSeconds(
                (long) sharedRoute.legs().get(0).duration()
        );

        double score = (WEIGHT_DETOUR   * detourScore(driverDistance, sharedRoute.distance()))
                     + (WEIGHT_COVERAGE * coverageScore(driverDistance, passengerDistance))
                     + (WEIGHT_TIME     *  timeScore(driverDepartureTime, passengerDepartureTime));

        return score;
    }

    public double timeScore(Instant driverArrival, Instant passengerArrival) {
        long driverWaits    = Math.max(0, Duration.between(driverArrival, passengerArrival).toMinutes());
        long passengerWaits = Math.max(0, Duration.between(passengerArrival, driverArrival).toMinutes());

        double driverScore    = Math.max(0.0, 1.0 - driverWaits    / MAX_WAIT_TIME_MINUTES);
        double passengerScore = Math.max(0.0, 1.0 - passengerWaits / MAX_WAIT_TIME_MINUTES);

        return Math.sqrt(driverScore * passengerScore);
    }

    private double coverageScore(double dDistance, double pDistance) {
        if (dDistance == 0 || pDistance == 0) return 0.0;

        double driverCoverage = Math.min(1.0, pDistance / dDistance);
        double passengerCoverage = Math.min(1.0, dDistance / pDistance);

        return  Math.sqrt(driverCoverage * passengerCoverage);
    }

    private double detourScore(double driverDistance, double sharedDistance){
        return driverDistance/sharedDistance;
    }
}