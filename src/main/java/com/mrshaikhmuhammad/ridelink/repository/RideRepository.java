package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.entity.*;
import org.bson.types.*;
import org.springframework.data.mongodb.repository.*;
import java.util.*;

public interface RideRepository extends MongoRepository<Ride, ObjectId> {

    public Optional<Ride> findById(ObjectId id);
}
