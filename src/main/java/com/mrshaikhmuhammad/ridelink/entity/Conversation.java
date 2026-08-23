package com.mrshaikhmuhammad.ridelink.entity;

import com.mrshaikhmuhammad.ridelink.entity.type.*;
import lombok.Builder;
import lombok.Data;
import org.bson.types.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.*;
import java.util.*;

@Data
@Document("conversation")
@Builder
public class Conversation {

    @Id
    ObjectId id;
    ConversationType type;

    @DBRef
    List<User> participants;

    @Indexed(unique = true, sparse = true)
    String directConversationKey;

    MessagePreview lastMessage;
    Instant createdAt;
}