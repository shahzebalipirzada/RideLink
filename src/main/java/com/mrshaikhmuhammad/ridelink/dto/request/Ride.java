package com.mrshaikhmuhammad.ridelink.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrshaikhmuhammad.ridelink.dto.request.GeoPoint;
import com.mrshaikhmuhammad.ridelink.dto.response.Path;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmRouteClient;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.Instant;
import java.util.List;

@Data
@Document("ride")
public class Ride {
    @Id
    ObjectId id;

    Role role; //driver passenger
    GeoPoint origin;
    GeoPoint destination;
    @JsonProperty("departure-time")
    Instant departureTime;

    Path path;

    public void setPath(OsrmRouteClient osrmClient){
        path = osrmClient.getRoute(
                List.of(origin, destination)
        );
    }
}