package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.entity.*;
import org.springframework.data.mongodb.repository.*;
import org.bson.types.*;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    List<Message> findByConversation(Conversation conversation);
}