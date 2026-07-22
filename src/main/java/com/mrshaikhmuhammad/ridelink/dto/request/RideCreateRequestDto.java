package com.mrshaikhmuhammad.ridelink.dto.request;

import com.mrshaikhmuhammad.ridelink.external.osrm.dto.*;
import java.time.Instant;

public record RideCreateRequestDto(
    String role,
    LocationRequestDto origin,
    LocationRequestDto destination,
    Instant departureTime
) {}