package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.dto.request.RideRequestDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideRepository extends MongoRepository<RideRequestDto, ObjectId> {
}
