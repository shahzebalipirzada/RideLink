package com.mrshaikhmuhammad.ridelink.dto.response;

import com.mrshaikhmuhammad.ridelink.entity.Ride;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;


public record RideResponseDto(
        List<RideSuggestion> rideSuggestion
) {
    public record RideSuggestion(
            String id,
            String origin,
            String destination,
            String departureTime
    ) {
        public RideSuggestion(Ride ride) {
            this(
                    ride.getId().toString(),
                    ride.getOrigin().getName(),

                    ride.getDestination().getName(),

                    DateTimeFormatter.ofPattern("MMMM d 'at' h:mm a")
                            .withZone(ZoneOffset.UTC)
                            .format(ride.getDepartureTime())
            );
        }
    }
}