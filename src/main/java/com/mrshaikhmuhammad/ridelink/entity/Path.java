package com.mrshaikhmuhammad.ridelink.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
public record Path(
        String code,
        Route routes
) {
    public record Route(
            double distance,
            double duration,
            double weight,

            @JsonProperty("weight_name")
            String weightName,
            String geometry
    ) {}
}