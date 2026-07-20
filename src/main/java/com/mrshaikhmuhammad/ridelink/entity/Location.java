package com.mrshaikhmuhammad.ridelink.entity;

import com.mrshaikhmuhammad.ridelink.external.osrm.dto.LocationRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Data
@Getter
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
