package com.mrshaikhmuhammad.ridelink.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.access.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> usernameNotFoundExceptionHandler(UsernameNotFoundException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsExceptionHandler(BadCredentialsException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> disabledExceptionHandler(DisabledException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiError> lockedExceptionHandler(LockedException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> authenticationExceptionHandler(AuthenticationException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> jwtExceptionHandler(JwtException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> accessDeniedExceptionHandler(AccessDeniedException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.FORBIDDEN);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ApiError> oauth2AuthenticationExceptionHandler(OAuth2AuthenticationException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApiError> invalidReefreshTokenException(InvalidRefreshTokenException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exceptionHandler(Exception ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return apiError.getResponseEntity();
    }
}