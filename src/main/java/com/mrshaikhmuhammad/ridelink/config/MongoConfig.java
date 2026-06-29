package com.mrshaikhmuhammad.ridelink.config;

import com.mrshaikhmuhammad.ridelink.dto.request.GeoPoint;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.bson.Document;

import java.util.List;

@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new Converter<Document, GeoPoint>() {
                    @Override
                    public GeoPoint convert(Document source) {
                        List<Double> coords = (List<Double>) source.get("coordinates");
                        return new GeoPoint(coords.get(0), coords.get(1));
                    }
                }
        ));
    }
}
