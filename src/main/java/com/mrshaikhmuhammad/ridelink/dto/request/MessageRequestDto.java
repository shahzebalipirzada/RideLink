package com.mrshaikhmuhammad.ridelink.dto.request;

public record MessageRequestDto (
    String receiver,
    String content
) {}