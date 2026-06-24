package com.mrshaikhmuhammad.ridelink.entity;

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

    @NonNull
    Role role;
    @NonNull
    GeoPoint origin;
    @NonNull
    GeoPoint destination;
    @NonNull
    Instant departureTime;

    Path path;

//    @DBRef
//    List<User> passengers;

//    public Ride(@NonNull Role role, @NonNull GeoPoint origin, @NonNull GeoPoint destination, @NonNull Instant departureTime, OsrmRouteClient osrmClient) {
//        this.role = role;
//        this.origin = origin;
//        this.destination = destination;
//        this.departureTime = departureTime;
//
//        path = osrmClient.getRoute(
//                List.of(origin, destination)
//        );

    public void setPath(OsrmRouteClient osrmClient){
        path = osrmClient.getRoute(
                List.of(origin, destination)
        );
    }
}