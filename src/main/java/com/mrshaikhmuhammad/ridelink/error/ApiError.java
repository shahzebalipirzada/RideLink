package com.mrshaikhmuhammad.ridelink.error;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime time;
    private HttpStatus status;
    private String message;

    ApiError(){
        time = LocalDateTime.now();
    }

    ApiError(String message, HttpStatus status){
        this.message = message;
        this.status = status;
        this.time = LocalDateTime.now();
    }

    ResponseEntity<ApiError> getResponseEntity(){
        return new ResponseEntity<>(this, status);
    }
}
