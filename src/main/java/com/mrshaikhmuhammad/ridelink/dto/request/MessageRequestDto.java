package com.mrshaikhmuhammad.ridelink.dto.request;

public record MessageRequestDto (
    String conversationId,
    String receiver,
    String rideId,
    String content
) {}