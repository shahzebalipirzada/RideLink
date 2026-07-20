package com.mrshaikhmuhammad.ridelink.entity;

import com.mrshaikhmuhammad.ridelink.dto.request.RideRequestDto;
import com.mrshaikhmuhammad.ridelink.entity.type.Role;
import com.mrshaikhmuhammad.ridelink.external.osrm.dto.LocationResponseDto;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.Instant;

@Data
@Document("ride")
@AllArgsConstructor
@NoArgsConstructor
public class Ride {
    @Id
    ObjectId id;

    Role role; //driver passenger
    Location origin;
    Location destination;

    @Field("departure_time")
    Instant departureTime;

    Path path;

    public Ride(RideRequestDto ride, LocationResponseDto path){
        this.role = Role.valueOf(ride.role());
        this.origin = new Location(ride.origin());
        this.destination = new Location(ride.destination());
        this.departureTime = ride.departureTime();
        this.path = new Path(path);
    }
}