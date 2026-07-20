package com.mrshaikhmuhammad.ridelink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;

public record SignupResponseDto (
    ObjectId id,
    String username
){}
