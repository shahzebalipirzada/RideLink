package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.entity.type.*;
import org.bson.types.*;
import org.springframework.data.mongodb.repository.*;
import java.util.*;

public interface UserRepository extends MongoRepository <User, ObjectId> {

    public Optional<User> findByUsername(String username);

    public Optional<User> findByProviderIdAndProviderType(String providerId, OauthProviderType providerType);
}