package com.mrshaikhmuhammad.ridelink.security.config;

import com.mrshaikhmuhammad.ridelink.security.JwtAuthFilter;
import com.mrshaikhmuhammad.ridelink.security.oauth_handler.OauthFailureHandler;
import com.mrshaikhmuhammad.ridelink.security.oauth_handler.OauthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();

    }
}


// 1. configure role based data access
// 2. implements sucess and failture handler
