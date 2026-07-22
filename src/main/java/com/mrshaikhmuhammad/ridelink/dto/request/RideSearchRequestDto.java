package com.mrshaikhmuhammad.ridelink.dto.request;

import com.mrshaikhmuhammad.ridelink.external.osrm.dto.*;
import java.time.*;

public record RideSearchRequestDto(
        String role, //driver passenger
        LocationRequestDto origin,
        LocationRequestDto destination,
        Instant departureTime
) {}
