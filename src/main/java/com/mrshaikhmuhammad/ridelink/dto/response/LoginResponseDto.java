package com.mrshaikhmuhammad.ridelink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    ObjectId userId;
    String accessToken;
}
