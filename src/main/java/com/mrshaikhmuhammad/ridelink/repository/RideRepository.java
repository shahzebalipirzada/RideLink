package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.entity.Ride;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RideRepository extends MongoRepository<Ride, ObjectId> {
}
