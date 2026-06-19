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
    GeoPoint source;
    @NonNull
    GeoPoint destination;

    @NonNull
    Instant departureTime;

    @DBRef
    List<User> passengers;
}

