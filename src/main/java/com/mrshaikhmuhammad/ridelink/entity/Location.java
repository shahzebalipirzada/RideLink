package com.mrshaikhmuhammad.ridelink.entity;

import com.mrshaikhmuhammad.ridelink.external.osrm.dto.LocationRequestDto;
import lombok.*;
import org.springframework.data.mongodb.core.geo.*;
import org.springframework.data.mongodb.core.index.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location{

    private String name;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint coordinate;

    public Location(LocationRequestDto dto){
        this.name = dto.name();
        coordinate = new GeoJsonPoint(dto.coordinate()[0], dto.coordinate()[1]);

    }
}