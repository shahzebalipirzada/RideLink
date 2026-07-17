package com.mrshaikhmuhammad.ridelink.security;


import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.entity.type.OauthProviderType;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Date;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    @Value("${security.jwt.key}")
    private String jwtSecurityKey;

    @Value("${security.jwt.access-token.age}")
    private int accessTokenAge;

    @Value("${security.jwt.refresh-token.age}")
    private int refreshTokenAge;

    private final UserRepository userRepository;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecurityKey.getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken(User user, int expiration) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("id", user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public ResponseCookie generateAccessTokenCookie(User user){
        return ResponseCookie.from("access_token", generateToken(user, accessTokenAge))
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(Duration.ofMillis(accessTokenAge))
                .build();
    }

    public ResponseCookie generateRefreshTokenCookie(User user){
        ResponseCookie  cookie = ResponseCookie.from("refresh_token", generateToken(user, refreshTokenAge))
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/auth/refresh-token")
                .maxAge(Duration.ofMillis(refreshTokenAge))
                .build();

        user.setRefreshToken(sha256(cookie.getValue()));
        userRepository.save(user);

        return cookie;
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public OauthProviderType getProviderType(String registrationId) {
        return switch (registrationId.toLowerCase()) {
            case "google" -> OauthProviderType.GOOGLE;

            case "github" -> OauthProviderType.GITHUB;

            default -> throw new ProviderNotFoundException("unsupported oauth2 provider exception.");
        };
    }

    public String getProviderId(OAuth2User user, String registrationId) {
        String providerId = switch (registrationId) {
            case "google" -> user.getAttribute("sub");
            case "github" -> user.getAttribute("id").toString();
            default -> throw new ProviderNotFoundException("unsupported oauth2 provider exception.");
        };

        if (providerId == null || providerId.isBlank()) {
            throw new ProviderNotFoundException("unable to determine oauth2 provider id exception.");
        }

        return providerId;
    }

    public String extractAccessToken(HttpServletRequest request){
        return extractToken(request, "access_token");
    }

    private String extractToken(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("user not authenticated");
        }

        String username = authentication.getPrincipal().toString();

        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found: " + username)
        );
    }

    public void invalidateRefreshToken(User user){
        user.setRefreshToken(null);
        userRepository.save(user);
    }

}