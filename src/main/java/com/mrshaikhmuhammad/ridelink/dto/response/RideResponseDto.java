package com.mrshaikhmuhammad.ridelink.dto.response;

import com.mrshaikhmuhammad.ridelink.entity.*;
import org.bson.types.*;

import java.util.*;
import java.time.*;
import java.time.format.*;

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