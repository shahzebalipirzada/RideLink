package com.mrshaikhmuhammad.ridelink.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("user")
public class User {

    @Id
    private ObjectId userId;

    @NonNull
    @Indexed
    private String username;

    @NonNull
    private String email;

}