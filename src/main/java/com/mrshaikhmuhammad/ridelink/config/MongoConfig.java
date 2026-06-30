package com.mrshaikhmuhammad.ridelink.config;

import com.mrshaikhmuhammad.ridelink.dto.request.GeoPoint;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.bson.Document;

import java.util.List;

@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new GeoPointToDocumentConverter(),
                new DocumentToGeoPointConverter()
        ));
    }

    @WritingConverter
    static class GeoPointToDocumentConverter implements Converter<GeoPoint, Document> {
        @Override
        public Document convert(GeoPoint source) {
            Document doc = new Document();
            doc.put("type", "Point");
            doc.put("coordinates", List.of(source.getX(), source.getY()));
            doc.put("name", source.getName());
            return doc;
        }
    }

    @ReadingConverter
    static class DocumentToGeoPointConverter implements Converter<Document, GeoPoint> {
        @Override
        public GeoPoint convert(Document source) {
            List<Double> coords = (List<Double>) source.get("coordinates");
            String name = source.getString("name");
            return new GeoPoint(name, coords.get(0), coords.get(1));
        }
    }
}
