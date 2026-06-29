package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.dto.request.Ride;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideRepository extends MongoRepository<Ride, ObjectId> {
}
