package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.dto.request.RideCreateRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.request.RideJoinRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.request.RideSearchRequestDto;
import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.dto.response.RideResponseDto;
import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmRouteClient;
import com.mrshaikhmuhammad.ridelink.repository.RideRepository;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import com.mrshaikhmuhammad.ridelink.security.AuthUtil;
import com.mrshaikhmuhammad.ridelink.service.scoring.RideScore;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RideService {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final OsrmRouteClient osrmClient;
    private final MongoTemplate mongoTemplate;
    private final RideScore similarityScorer;
    private final AuthUtil authUtil;

    private final int MAX_WAIT_SECONDS = 2*60*60;

    @Transactional
    public void createRide(RideCreateRequestDto dto){
        User user = authUtil.getAuthenticatedUser();
        Ride ride = new Ride(
            dto,
            osrmClient.getRoute(List.of(dto.origin(), dto.destination()))
        );
        ride.setCreator(user);
        rideRepository.save(ride);
    }

    @Transactional
    public void joinRide(RideJoinRequestDto dto){
        User user = authUtil.getAuthenticatedUser();

        Ride ride = rideRepository.findById(dto.id())
            .orElseThrow(() -> new IllegalArgumentException(
                    "Ride Not Found: no ride found with this id " + dto.id().toString()
                )
            );

        if(ride.getJoiners() == null){
            ride.setJoiners(new HashSet<>());
        }
        if (!ride.getCreator().equals(user)) {
            ride.getJoiners().add(user);
        }
        else{
            throw new IllegalArgumentException("Owner can not Join as Passenger: ride owner is trying to join his own ride");
        }
        rideRepository.save(ride);
    }

    public RideResponseDto searchRides(RideSearchRequestDto dto, int radius){
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