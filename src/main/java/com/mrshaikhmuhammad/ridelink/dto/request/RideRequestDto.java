package com.mrshaikhmuhammad.ridelink.dto.request;

import com.mrshaikhmuhammad.ridelink.dto.response.LocationResponseDto;
import com.mrshaikhmuhammad.ridelink.entity.type.Role;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmRouteClient;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.Instant;
import java.util.List;

@Data
@Document("ride")
public class RideRequestDto {
    @Id
    ObjectId id;

    Role role; //driver passenger
    LocationRequestDto origin;
    LocationRequestDto destination;

    @Field("departure_time")
    Instant departureTime;

    LocationResponseDto path;

    public void setPath(OsrmRouteClient osrmClient){
        path = osrmClient.getRoute(
                List.of(origin, destination)
        );
    }
}