package com.mrshaikhmuhammad.ridelink.dto.response;

import org.bson.types.*;

public record SignupResponseDto (
    ObjectId id,
    String username
) {}