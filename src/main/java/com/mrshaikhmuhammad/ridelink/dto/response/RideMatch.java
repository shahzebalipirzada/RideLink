package com.mrshaikhmuhammad.ridelink.dto.response;

import com.mrshaikhmuhammad.ridelink.dto.request.Ride;

public record RideMatch(
        String origin,
        String destination,
        String departureTime
) {
    public RideMatch(Ride ride) {
        this(
                ride.getOrigin().toString(),
                ride.getDestination().toString(),
                ride.getDepartureTime().toString()
        );
    }
}