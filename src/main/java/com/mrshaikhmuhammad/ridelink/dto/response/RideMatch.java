package com.mrshaikhmuhammad.ridelink.dto.response;

import com.mrshaikhmuhammad.ridelink.dto.request.Ride;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public record RideMatch(
        String origin,
        String destination,
        String departureTime
) {
    public RideMatch(Ride ride) {
        this(
                ride.getOrigin().getName(),

                ride.getDestination().getName(),

                DateTimeFormatter.ofPattern("MMMM d 'at' h:mm a")
                        .withZone(ZoneOffset.UTC)
                        .format(ride.getDepartureTime())
        );
    }
}