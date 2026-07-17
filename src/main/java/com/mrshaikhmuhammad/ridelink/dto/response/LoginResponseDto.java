package com.mrshaikhmuhammad.ridelink.dto.response;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseCookie;

public record LoginResponseDto (
    ObjectId userId,
    ResponseCookie accessTokenCookie,
    ResponseCookie refreshTokenCookie
){}
