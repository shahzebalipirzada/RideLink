package com.mrshaikhmuhammad.ridelink.security.config;

import com.mrshaikhmuhammad.ridelink.security.*;
import com.mrshaikhmuhammad.ridelink.security.handler.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    OauthSuccessHandler successHandler;

    @Autowired
    OauthFailureHandler failureHandler;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/ride/**", "/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(
                        oauth -> oauth
                                .successHandler(successHandler)
                                .failureHandler(failureHandler)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
}