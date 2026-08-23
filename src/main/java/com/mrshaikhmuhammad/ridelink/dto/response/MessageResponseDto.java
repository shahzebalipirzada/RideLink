package com.mrshaikhmuhammad.ridelink.dto.response;

public record MessageResponseDto(
        String conversationId,
        String sender,
        String content
) {}
