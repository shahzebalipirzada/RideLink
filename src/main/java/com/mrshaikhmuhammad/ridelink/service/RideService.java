package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.dto.request.RideRequestDto;
import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.dto.response.RideResponseDto;
import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmRouteClient;
import com.mrshaikhmuhammad.ridelink.repository.RideRepository;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import com.mrshaikhmuhammad.ridelink.security.AuthUtil;
import com.mrshaikhmuhammad.ridelink.service.scoring.RideScore;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RideService {

    private final UserRepository userRepository;
    private final OsrmRouteClient osrmClient;
    private final MongoTemplate mongoTemplate;
    private final RideScore similarityScorer;
    private final AuthUtil authUtil;

    private final int MAX_WAIT_SECONDS = 2*60*60;

    public void createRide(RideRequestDto dto){
        User user = authUtil.getAuthenticatedUser();
        Ride ride = new Ride(
            dto,
            osrmClient.getRoute(List.of(dto.origin(), dto.destination()))
        );

        if(user.getCreatedRides() == null){
            user.setCreatedRides(new ArrayList<>());
        }
        user.getCreatedRides().add(ride);
        userRepository.save(user);
    }

    public RideResponseDto searchRides(RideRequestDto dto, int radius){
        Ride ride = new Ride(
            dto,
            osrmClient.getRoute(List.of(dto.origin(), dto.destination()))
        );

        List<Ride> candidates = filterRide(ride, radius);

        List<RideResponseDto.RideSuggestion> suggestions = candidates.stream()
                .map(candidate -> Map.entry(candidate, similarityScorer.score(ride, candidate)))
                .sorted(Map.Entry.<Ride, Double>comparingByValue().reversed())
                .map(entry -> new RideResponseDto.RideSuggestion(entry.getKey()))
                .toList();

        return new RideResponseDto(suggestions);
    }

    private List<Ride> filterRide(Ride ride, int radius){
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("_id").ne(ride.getId()),

                Criteria.where("departureTime")
                        .gt(ride.getDepartureTime().minusSeconds(MAX_WAIT_SECONDS))
                        .lt(ride.getDepartureTime().plusSeconds(MAX_WAIT_SECONDS)),

                Criteria.where("origin.coordinate")
                        .withinSphere(new Circle(
                                ride.getOrigin().getCoordinate(),
                                new Distance(radius, Metrics.KILOMETERS)
                        ))
        );

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Ride.class);
    }
}