package com.mrshaikhmuhammad.ridelink.repository;

import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.entity.type.ConversationType;
import org.springframework.data.mongodb.repository.*;
import org.bson.types.*;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends MongoRepository<Conversation, ObjectId> {
    List<Conversation> findByParticipantsContains(User user);
    Optional<Conversation> findByDirectConversationKey(String participantsKey);
}