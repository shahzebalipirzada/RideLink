package com.mrshaikhmuhammad.ridelink.external.osrm.route;

import com.mrshaikhmuhammad.ridelink.entity.GeoPoint;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmProperties;
import com.mrshaikhmuhammad.ridelink.external.osrm.route.dto.OsrmRouteResponse;

import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

@Component
@RequiredArgsConstructor
public class OsrmRouteClient {

    private final RestTemplate restTemplate;
    private final OsrmProperties osrmProperties;


    private String buildUrl(GeoPoint origin, GeoPoint destination){
        return "%s/%s/v1/%s/%s;%s?%s"
                .formatted(
                        osrmProperties.baseUrl(),
                        osrmProperties.route().service(),
                        osrmProperties.profile(),
                        origin,
                        destination,
                        osrmProperties.route().option()
                );
    }

    public OsrmRouteResponse getRoute(GeoPoint origin, GeoPoint destination){
        String url = buildUrl(origin, destination);
        ResponseEntity<OsrmRouteResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, OsrmRouteResponse.class);
        return response.getBody();
    }
}

