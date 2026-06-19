package com.mrshaikhmuhammad.ridelink.external.osrm;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.*;

@ConfigurationProperties(prefix = "osrm")
public record OsrmProperties(

    @NonNull
    @Validated
   String baseUrl,
   String profile,
   Route route
) {
    public record Route(
            String service,
            List<Option> options
    ) {
        public String option(){
            if (options == null || options.isEmpty())
                return "";

            return options.stream()
                    .map(Option::toString)
                    .collect(Collectors.joining("&"));
        }
    }

    public record Option(
            String option,
            String value
    ) {
        @Override
        public String toString() {
            return option + "=" + value;
        }
    }
}
