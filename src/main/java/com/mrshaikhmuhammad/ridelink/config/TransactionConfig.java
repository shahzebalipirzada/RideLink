package com.mrshaikhmuhammad.ridelink.config;

import org.springframework.transaction.*;
import org.springframework.data.mongodb.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    PlatformTransactionManager platformTransactionManager(MongoDatabaseFactory mongoDatabaseFactory){
        return new MongoTransactionManager(mongoDatabaseFactory);
    }
}