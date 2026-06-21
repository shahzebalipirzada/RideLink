package com.mrshaikhmuhammad.ridelink.external.osrm.trip;

import com.mrshaikhmuhammad.ridelink.entity.GeoPoint;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmProperties;
import com.mrshaikhmuhammad.ridelink.entity.Path;

import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class OsrmTripClient {

    private final RestTemplate restTemplate;
    private final OsrmProperties osrmProperties;


    private String buildUrl(GeoPoint origin, List<GeoPoint> stops, GeoPoint destination){
        String coordinates = Stream.concat(
            Stream.concat(
               Stream.of(origin),
               stops.stream()
            ),
            Stream.of(destination)
        )
        .map(GeoPoint :: toString)
        .collect(Collectors.joining(";"));

        return "%s/%s/v1/%s/%s?%s"
            .formatted(
                osrmProperties.baseUrl(),
                osrmProperties.trip().service(),
                osrmProperties.profile(),
                coordinates,
                osrmProperties.trip().option()
            );
    }

    public Path getTrip(GeoPoint origin, List<GeoPoint> stops, GeoPoint destination){
        String url = buildUrl(origin, stops, destination);
        ResponseEntity<Path> response = restTemplate.exchange(url, HttpMethod.GET, null, Path.class);
        return response.getBody();
    }
}

