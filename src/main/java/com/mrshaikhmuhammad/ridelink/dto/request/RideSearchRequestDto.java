package com.mrshaikhmuhammad.ridelink.dto.request;

import com.mrshaikhmuhammad.ridelink.external.osrm.dto.LocationRequestDto;

import java.time.Instant;

public record RideSearchRequestDto(
        String role, //driver passenger
        LocationRequestDto origin,
        LocationRequestDto destination,
        Instant departureTime
){}
