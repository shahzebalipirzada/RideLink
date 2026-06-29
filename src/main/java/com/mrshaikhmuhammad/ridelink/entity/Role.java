package com.mrshaikhmuhammad.ridelink.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    DRIVER,
    PASSENGER;

    @JsonCreator
    public static Role fromValue(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}
