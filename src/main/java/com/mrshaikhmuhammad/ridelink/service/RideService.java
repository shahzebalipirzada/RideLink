package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.dto.request.RideRequestDto;
import com.mrshaikhmuhammad.ridelink.dto.response.RideResponseDto;
import com.mrshaikhmuhammad.ridelink.external.osrm.OsrmRouteClient;
import com.mrshaikhmuhammad.ridelink.repository.RideRepository;
import com.mrshaikhmuhammad.ridelink.service.scoring.RideScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RideService {

    @Autowired
    RideRepository rideRepository;
    @Autowired
    OsrmRouteClient osrmClient;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    RideScore similarityScorer;

    private final int MAX_WAIT_SECONDS = 2*60*60;

    public void saveRide(RideRequestDto ride){
        ride.setPath(osrmClient);
        rideRepository.save(ride);
    }

    public List<RideResponseDto> searchRides(RideRequestDto requestRide, int radius){
        requestRide.setPath(osrmClient);
        List<RideRequestDto> candidates = filterRide(requestRide, radius);

        return candidates.stream()
                .map(candidate -> Map.entry(candidate, similarityScorer.score(requestRide, candidate)))
                .sorted(Map.Entry.<RideRequestDto, Double>comparingByValue().reversed())
                .map(entry -> new RideResponseDto(entry.getKey()))
                .toList();
    }

    private List<RideRequestDto> filterRide(RideRequestDto requestRide, int radius){
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("_id").ne(requestRide.getId()),

                Criteria.where("departureTime")
                        .gt(requestRide.getDepartureTime().minusSeconds(MAX_WAIT_SECONDS))
                        .lt(requestRide.getDepartureTime().plusSeconds(MAX_WAIT_SECONDS)),

                Criteria.where("origin")
                        .withinSphere(new Circle(
                                requestRide.getOrigin(),
                                new Distance(radius, Metrics.KILOMETERS)
                        ))
        );

        Query query = new Query(criteria);
        return mongoTemplate.find(query, RideRequestDto.class);
    }
}