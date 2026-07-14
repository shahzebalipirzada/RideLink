package com.mrshaikhmuhammad.ridelink.external.osrm;

import com.mrshaikhmuhammad.ridelink.dto.request.LocationRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.response.LocationResponseDto;

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


    private String buildUrl(List<LocationRequestDto> stops){
        String coordinates = stops.stream()
                .map(LocationRequestDto::toString)
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

    public LocationResponseDto getRoute(List<LocationRequestDto> stops){
        String url = buildUrl(stops);
        ResponseEntity<LocationResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, null, LocationResponseDto.class);
        return response.getBody();
    }
}

