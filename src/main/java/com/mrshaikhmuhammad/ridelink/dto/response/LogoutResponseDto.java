package com.mrshaikhmuhammad.ridelink.dto.response;

import org.springframework.http.*;

public record LogoutResponseDto(
        ResponseCookie accessTokenCookie,
        ResponseCookie refreshTokenCookie
) {}
