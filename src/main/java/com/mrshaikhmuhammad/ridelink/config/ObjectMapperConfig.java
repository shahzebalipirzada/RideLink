package com.mrshaikhmuhammad.ridelink.config;

import org.springframework.context.annotation.*;
import com.fasterxml.jackson.databind.*;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}