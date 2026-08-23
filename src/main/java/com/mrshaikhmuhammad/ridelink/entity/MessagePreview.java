package com.mrshaikhmuhammad.ridelink.entity;

import lombok.*;
import org.bson.types.*;
import java.time.*;

@Data
@Builder
public class MessagePreview {
    ObjectId messageId;
    String senderName;
    String contentPreview;
    Instant timestamp;
}