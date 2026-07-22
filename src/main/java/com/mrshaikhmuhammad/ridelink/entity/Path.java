package com.mrshaikhmuhammad.ridelink.entity;

import com.fasterxml.jackson.annotation.*;
import com.mrshaikhmuhammad.ridelink.external.osrm.dto.*;
import java.util.*;

public record Path(
        String code,
        List<Route> routes,
        List<Waypoint> waypoints
) {

    public Path(LocationResponseDto dto) {
        this(
            dto.code(),

            dto.routes().stream()
                .map(r -> new Route(
                    r.distance(),
                    r.duration(),
                    r.weight(),
                    r.legs().stream()
                        .map(l -> new Leg(l.duration(), l.distance(), l.weight()))
                        .toList(),
                    r.weightName(),
                    r.geometry()
                    )
                )
                .toList(),

            dto.waypoints().stream()
                .map(w -> new Waypoint(w.hint(), w.location(), w.name(), w.distance()))
                .toList()
        );
    }

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