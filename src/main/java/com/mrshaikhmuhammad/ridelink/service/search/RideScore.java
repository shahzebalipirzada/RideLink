package com.mrshaikhmuhammad.ridelink.service.search;

import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.external.osrm.route.OsrmRouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class RideScore {

    @Autowired
    OsrmRouteClient tripClient;

    private static final double WEIGHT_TIME     = 0.40;
    private static final double WEIGHT_DETOUR   = 0.45;
    private static final double WEIGHT_COVERAGE = 0.15;

    private double finalScore(Ride driver, Ride passenger) {
        if (driver.getPath() == null || passenger.getPath() == null)
            return 0.0;

        double score = (WEIGHT_DETOUR   * detourScore(driver, passenger))
                     + (WEIGHT_COVERAGE * coverageScore(driver, passenger))
                     + (WEIGHT_TIME     *  timeScore(driver, passenger));

        return score;
    }

    public double timeScore(Ride driver, Ride passenger) {
        long diffMinutes = Math.abs(Duration.between(
                driver.getArrivalTime(),
                passenger.getArrivalTime()
        ).toMinutes());

        return Math.max(0.0, 1.0 - diffMinutes / 60.0);
    }

    private double coverageScore(Ride driver, Ride passenger) {
        Path.Route dRoute = driver.getPath().routes();
        Path.Route pRoute = passenger.getPath().routes();

        if (dRoute.distance() == 0 || pRoute.distance() == 0) {
            return 0.0;
        }

        double driverCoverage = Math.min(1.0, pRoute.distance() / dRoute.distance());
        double passengerCoverage = Math.min(1.0, dRoute.distance() / pRoute.distance());

        return Math.min(driverCoverage, passengerCoverage);
    }

    private double detourScore(Ride driver, Ride passenger){
        GeoPoint dOrigin = driver.getOrigin();
        GeoPoint dDestination = driver.getDestination();
        GeoPoint pOrigin = passenger.getOrigin();
        GeoPoint pDestination = passenger.getDestination();

        Path sharedPath = tripClient.getTrip(
            List.of(dOrigin, pOrigin, pDestination, dDestination)
        );

        double dDistance = driver.getPath().routes().distance();
        double pDistance = passenger.getPath().routes().distance();
        double sharedDistance = sharedPath.routes().distance();

        double dScore = dDistance/sharedDistance;
        double pScore = pDistance/sharedDistance;

        return Math.min(dScore, pScore);
    }
}