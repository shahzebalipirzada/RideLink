package com.mrshaikhmuhammad.ridelink.external.osrm.route.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OsrmRouteResponse(
        String code,
        List<Route> routes
) {
    public record Route(
            double distance,
            double duration,
            double weight,

            @JsonProperty("weight_name")
            String weightName,
            Object geometry
    ) {}

    public record Geometry(
            String type,                      // "LineString"
            List<List<Double>> coordinates    // [[lng, lat], [lng, lat], ...]
    ) {}
}