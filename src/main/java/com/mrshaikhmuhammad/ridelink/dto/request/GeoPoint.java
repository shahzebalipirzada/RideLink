package com.mrshaikhmuhammad.ridelink.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = ValueDeserializer.None.class)
public class GeoPoint extends GeoJsonPoint{

    public GeoPoint(double longitude, double latitude){
        super(longitude, latitude);
    }

    @Override
    public String toString(){
        return String.format("%.6f,%.6f", getX(), getY());
    }

    @JsonCreator
    public static GeoPoint fromJson(
            @JsonProperty("coordinates") List<Double> coordinates
    ) {
        if (coordinates == null || coordinates.size() < 2) {
            throw new IllegalArgumentException("coordinates must have at least 2 elements");
        }
        return new GeoPoint(coordinates.get(0), coordinates.get(1));
    }
}
