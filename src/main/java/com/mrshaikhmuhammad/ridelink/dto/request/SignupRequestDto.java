package com.mrshaikhmuhammad.ridelink.dto.request;

public record SignupRequestDto (
    String name,
    String username,
    String password
){ }