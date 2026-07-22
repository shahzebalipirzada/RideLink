package com.mrshaikhmuhammad.ridelink.error;

import lombok.*;
import org.springframework.http.*;
import java.time.*;

@Data
public class ApiError {

    private LocalDateTime time;
    private HttpStatus status;
    private String message;

    ApiError(String message, HttpStatus status){
        this.message = message;
        this.status = status;
        this.time = LocalDateTime.now();
    }

    ResponseEntity<ApiError> getResponseEntity(){
        return new ResponseEntity<>(this, status);
    }
}
