package com.mrshaikhmuhammad.ridelink.entity;

import com.fasterxml.jackson.annotation.*;
import java.util.*;

public record Path(
        String code,
        List<Route> routes,
        List<Waypoint> waypoints
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Route(
            double distance,
            double duration,
            double weight,
            List<Leg> legs,

            @JsonProperty("weight_name")
            String weightName,
            String geometry
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Waypoint(
            String hint,
            List<Double> location,  // [longitude, latitude]
            String name,
            double distance
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Leg(
            double duration,
            double distance,
            double weight
    ) {}
}