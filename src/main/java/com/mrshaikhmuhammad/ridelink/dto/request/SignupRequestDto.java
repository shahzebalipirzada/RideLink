package com.mrshaikhmuhammad.ridelink.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    String name;
    String username;
    String password;
}