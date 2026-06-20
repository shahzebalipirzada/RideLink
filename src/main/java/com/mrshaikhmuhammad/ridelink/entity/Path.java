package com.mrshaikhmuhammad.ridelink.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Path(
        String code,
        List<Route> routes
) {
    public record Route(
            double distance,
            double duration,
            double weight,

            @JsonProperty("weight_name")
            String weightName,
            Geometry geometry
    ) {}

    public record Geometry(
            String type,                      // "LineString"
            List<List<Double>> coordinates    // [[lng, lat], [lng, lat], ...]
    ) {}
}