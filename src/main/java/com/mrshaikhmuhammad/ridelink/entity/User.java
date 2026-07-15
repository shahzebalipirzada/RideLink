package com.mrshaikhmuhammad.ridelink.entity;


import com.mrshaikhmuhammad.ridelink.entity.type.OauthProviderType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Document("user")
public class User implements UserDetails {

    @Id
    private ObjectId id;

    private String name;
    @NonNull
    @Indexed
    private String username;
    private String password;
    private String providerId;
    private OauthProviderType providerType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}