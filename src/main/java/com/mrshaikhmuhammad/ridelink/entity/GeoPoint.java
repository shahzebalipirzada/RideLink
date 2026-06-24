package com.mrshaikhmuhammad.ridelink.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Locale;

@Data
public class GeoPoint extends GeoJsonPoint {

    public GeoPoint(double longitude, double latitude){
        super(longitude, latitude);
    }

    @Override
    public String toString(){
        return String.format("%.6f,%.6f", getX(), getY());
    }
}
