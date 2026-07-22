package com.mrshaikhmuhammad.ridelink.dto.response;

import org.springframework.http.*;

public record RefreshTokenResponseDto(
        ResponseCookie accessTokenCookie,
        ResponseCookie refreshTokenCookie
) {}