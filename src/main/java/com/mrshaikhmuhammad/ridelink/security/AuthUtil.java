package com.mrshaikhmuhammad.ridelink.security;

import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.entity.type.OauthProviderType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {
    @Value("${security.jwt.key}")
    private String jwtSecurityKey;

    @Value("${security.jwt.access-token.age}")
    private int accessTokenAge;

    @Value("${security.jwt.refresh-token.age}")
    private int refreshTokenAge;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecurityKey.getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken(User user, int expiration){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("id", user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateAccessToken(User user){
        return generateToken(user, accessTokenAge);
    }

    public String generateRefreshToken(User user){
        return generateToken(user, refreshTokenAge);
    }


    public String getUsername(String token) {
        return Jwts.parser().verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public OauthProviderType getProviderType(String registrationId){
        return switch(registrationId.toLowerCase()){
            case "google" -> OauthProviderType.GOOGLE;

            case "github" -> OauthProviderType.GITHUB;

            default -> throw new ProviderNotFoundException("unsupported oauth2 provider exception.");
        };
    }

    public String getProviderId(OAuth2User user, String registrationId){
        String providerId = switch(registrationId){
            case "google" -> user.getAttribute("sub");
            case "github" -> user.getAttribute("id").toString();
            default -> throw new ProviderNotFoundException("unsupported oauth2 provider exception.");
        };

        if(providerId == null || providerId.isBlank()){
            throw new ProviderNotFoundException("unable to determine oauth2 provider id exception.");
        }

        return providerId;
    }

    public String getUsername(OAuth2User user, String registrationId, String providerId){
        String email = user.getAttribute("email");
        if(email != null){
            return email;
        }
        else{
            return switch(registrationId){
                case "google" -> user.getAttribute("sub");
                case "github" -> user.getAttribute("login");
                default -> providerId;
            };
        }
    }
}