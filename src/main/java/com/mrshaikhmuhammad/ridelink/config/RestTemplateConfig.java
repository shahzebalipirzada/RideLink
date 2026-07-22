package com.mrshaikhmuhammad.ridelink.config;

import org.springframework.web.client.*;
import org.springframework.context.annotation.*;

@Configuration
public class RestTemplateConfig {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}