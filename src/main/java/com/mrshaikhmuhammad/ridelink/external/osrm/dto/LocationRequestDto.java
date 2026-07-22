package com.mrshaikhmuhammad.ridelink.external.osrm.dto;

import com.mrshaikhmuhammad.ridelink.entity.*;

public record LocationRequestDto(String name, double[] coordinate) {

    public LocationRequestDto(Location location) {
        this(
            location.getName(),
            new double[]{location.getCoordinate().getX(), location.getCoordinate().getY()}
        );
    }

    @Override
    public String toString() {
        return String.format("%.6f,%.6f", coordinate[0], coordinate[1]);
    }
}