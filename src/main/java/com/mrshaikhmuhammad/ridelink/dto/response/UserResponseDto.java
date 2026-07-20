package com.mrshaikhmuhammad.ridelink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public record UserResponseDto (
    String name,
    String email
){}
