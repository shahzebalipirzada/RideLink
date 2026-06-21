package com.mrshaikhmuhammad.ridelink.external.osrm.route;

import com.mrshaikhmuhammad.ridelink.entity.GeoPoint;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmProperties;
import com.mrshaikhmuhammad.ridelink.entity.Path;

import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OsrmRouteClient {

    private final RestTemplate restTemplate;
    private final OsrmProperties osrmProperties;


    private String buildUrl(List<GeoPoint> stops){
        String coordinates = stops.stream()
                .map(GeoPoint::toString)
                .collect(Collectors.joining(";"));

        return "%s/%s/v1/%s/%s?%s"
            .formatted(
                osrmProperties.baseUrl(),
                osrmProperties.route().service(),
                osrmProperties.profile(),
                coordinates,
                osrmProperties.route().option()
            );
    }

    public Path getTrip(List<GeoPoint> stops){
        String url = buildUrl(stops);
        ResponseEntity<Path> response = restTemplate.exchange(url, HttpMethod.GET, null, Path.class);
        return response.getBody();
    }
}

