package com.mrshaikhmuhammad.ridelink.dto.response;

import com.mrshaikhmuhammad.ridelink.dto.request.RideRequestDto;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public record RideResponseDto(
        String origin,
        String destination,
        String departureTime
) {
    public RideResponseDto(RideRequestDto ride) {
        this(
                ride.getOrigin().getName(),

                ride.getDestination().getName(),

                DateTimeFormatter.ofPattern("MMMM d 'at' h:mm a")
                        .withZone(ZoneOffset.UTC)
                        .format(ride.getDepartureTime())
        );
    }
}