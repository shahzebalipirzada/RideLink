package com.mrshaikhmuhammad.ridelink;

import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.external.osrm.*;
import com.mrshaikhmuhammad.ridelink.service.RideService;

import org.springframework.boot.*;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Instant;

@SpringBootApplication
@EnableConfigurationProperties(OsrmProperties.class)
public class RideLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideLinkApplication.class, args);
    }

}
