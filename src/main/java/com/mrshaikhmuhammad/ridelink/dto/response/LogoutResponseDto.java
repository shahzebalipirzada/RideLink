package com.mrshaikhmuhammad.ridelink.dto.response;

import org.apache.coyote.Response;
import org.springframework.http.ResponseCookie;

public record LogoutResponseDto(ResponseCookie accessTokenCookie, ResponseCookie refreshTokenCookie) {
}
