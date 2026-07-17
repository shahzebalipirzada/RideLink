package com.mrshaikhmuhammad.ridelink.security;

import com.mrshaikhmuhammad.ridelink.dto.response.LoginResponseDto;
import org.springframework.http.ResponseCookie;

public record LoginResponse(LoginResponseDto loginResponseDto, ResponseCookie accessTokenCookie, ResponseCookie refreshTokenCookie) {
}
