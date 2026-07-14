package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.entity.type.OauthProviderType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository <User, ObjectId> {
    public Optional<User> findByUsername(String username);

    public Optional<User> findByProviderIdAndProviderType(String providerId, OauthProviderType providerType);

}
