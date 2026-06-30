package com.mrshaikhmuhammad.ridelink.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@Getter
@JsonDeserialize(using = ValueDeserializer.None.class)
public class GeoPoint extends GeoJsonPoint{
    private String name;
    public GeoPoint(String name, double longitude, double latitude){
        super(longitude, latitude);
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("%.6f,%.6f", getX(), getY());
    }

    @JsonCreator
    public static GeoPoint fromJson(
            @JsonProperty("coordinates") List<Double> coordinates,
            @JsonProperty("name") String name
    ) {
        if (coordinates == null || coordinates.size() < 2) {
            throw new IllegalArgumentException("coordinates must have at least 2 elements");
        }
        return new GeoPoint(name, coordinates.get(0), coordinates.get(1));
    }
}
