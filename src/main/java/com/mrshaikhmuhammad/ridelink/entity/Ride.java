package com.mrshaikhmuhammad.ridelink.entity;

import com.mrshaikhmuhammad.ridelink.external.osrm.route.OsrmRouteClient;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;


@Data
@Document("ride")
public class Ride {
    @Id
    ObjectId rideId;

    @NonNull
    GeoPoint origin;
    @NonNull
    GeoPoint destination;

    Path path;

    @DBRef
    List<User> passengers;
}

