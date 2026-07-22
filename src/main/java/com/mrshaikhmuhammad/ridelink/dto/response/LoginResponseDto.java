package com.mrshaikhmuhammad.ridelink.dto.response;

import org.bson.types.*;
import org.springframework.http.*;

public record LoginResponseDto (
    ObjectId userId,
    ResponseCookie accessTokenCookie,
    ResponseCookie refreshTokenCookie
) {}