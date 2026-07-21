package com.mrshaikhmuhammad.ridelink.dto.request;

import com.mrshaikhmuhammad.ridelink.external.osrm.dto.LocationRequestDto;

import java.time.Instant;

public record RideCreateRequestDto(
    LocationRequestDto origin,
    LocationRequestDto destination,
    Instant departureTime
){}