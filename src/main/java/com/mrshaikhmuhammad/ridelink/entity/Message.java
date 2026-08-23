package com.mrshaikhmuhammad.ridelink.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import java.time.*;

@Data
@Document("message")
@Builder
public class Message {

    @Id
    ObjectId id;

    @DBRef
    Conversation conversation;

    @DBRef
    User sender;
    String senderName;
    String content;
    Instant timestamp;
}