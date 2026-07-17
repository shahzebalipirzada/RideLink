package com.mrshaikhmuhammad.ridelink.dto.response;

import org.springframework.http.ResponseCookie;

public record RefreshTokenResponseDto(ResponseCookie accessTokenCookie, ResponseCookie refreshTokenCookie) {
}
