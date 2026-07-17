package com.mrshaikhmuhammad.ridelink.error;

import io.jsonwebtoken.JwtException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> usernameNotFoundExceptionHanlder(UsernameNotFoundException ex){
        ApiError apiError = new ApiError("username not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsExceptionHandler(BadCredentialsException ex){
        ApiError apiError = new ApiError("Invalid username or password", HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> disabledExceptionHandler(DisabledException ex){
        ApiError apiError = new ApiError("Account is disabled", HttpStatus.FORBIDDEN);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiError> lockedExceptionHandler(LockedException ex){
        ApiError apiError = new ApiError("Account is locked", HttpStatus.FORBIDDEN);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> authenticationExceptionHanlder(AuthenticationException ex){
        ApiError apiError = new ApiError("authentication failed: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> jwtExceptionHanlder(JwtException ex){
        ApiError apiError = new ApiError("invalid jwt token: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> accessDeniedExceptionHanlder(AccessDeniedException ex){
        ApiError apiError = new ApiError("access denied insufficient persmissions: " + ex.getMessage(), HttpStatus.FORBIDDEN);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ApiError> oauth2AuthenticationExceptionHanlder(OAuth2AuthenticationException ex){
        ApiError apiError = new ApiError("oauth authentication failed: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exceptionHanlder(Exception ex){
        ApiError apiError = new ApiError("unexcepted exception occured: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return apiError.getResponseEntity();
    }
}