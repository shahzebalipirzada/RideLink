package com.mrshaikhmuhammad.ridelink.entity;

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
    ObjectId rideId;

    @NonNull
    Role role;
    @NonNull
    GeoPoint origin;
    @NonNull
    GeoPoint destination;

    Instant departureTime;
    Path path;

    @DBRef
    List<User> passengers;
}

