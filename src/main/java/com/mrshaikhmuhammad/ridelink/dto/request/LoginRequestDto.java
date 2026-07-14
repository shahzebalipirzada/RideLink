package com.mrshaikhmuhammad.ridelink.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    String username;
    String password;
}
