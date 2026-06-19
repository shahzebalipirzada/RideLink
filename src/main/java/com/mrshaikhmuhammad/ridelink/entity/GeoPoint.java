package com.mrshaikhmuhammad.ridelink.entity;

import java.util.Locale;

public record GeoPoint(double longitude, double latitude) {
    @Override
    public String toString(){
        return String.format("%.6f,%.6f", longitude, latitude);
    }
}
