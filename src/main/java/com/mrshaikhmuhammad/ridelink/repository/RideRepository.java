package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RideRepository extends MongoRepository<Ride, ObjectId> {
    public Optional<Ride> findById(ObjectId id);
}
