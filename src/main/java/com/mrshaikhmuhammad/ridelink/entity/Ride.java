package com.mrshaikhmuhammad.ridelink.entity;

import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.entity.type.*;
import com.mrshaikhmuhammad.ridelink.external.osrm.dto.*;

import lombok.*;
import org.bson.types.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.*;
import java.util.*;

@Data
@Document("ride")
@AllArgsConstructor
@NoArgsConstructor
public class Ride {

    @Id
    ObjectId id;

    Location origin;
    Location destination;

    @DBRef
    User creator;
    Role creatorRole;

    @DBRef
    Set<User> joiners = new HashSet<>();

    @Field("departure_time")
    Instant departureTime;

    Path path;

    public Ride(RideCreateRequestDto ride, LocationResponseDto path){
        this.creatorRole = Role.valueOf(ride.role());
        this.origin = new Location(ride.origin());
        this.destination = new Location(ride.destination());
        this.departureTime = ride.departureTime();
        this.path = new Path(path);
    }

    public Ride(RideSearchRequestDto ride, LocationResponseDto path){
        this.creatorRole = Role.valueOf(ride.role());
        this.origin = new Location(ride.origin());
        this.destination = new Location(ride.destination());
        this.departureTime = ride.departureTime();
        this.path = new Path(path);
    }
}