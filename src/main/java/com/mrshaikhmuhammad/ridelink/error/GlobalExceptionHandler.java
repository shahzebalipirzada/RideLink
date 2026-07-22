package com.mrshaikhmuhammad.ridelink.error;

import io.jsonwebtoken.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.core.*;
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
    public ResponseEntity<ApiError> invalidRefreshTokenException(InvalidRefreshTokenException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> illegalArgumentException(IllegalArgumentException ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return apiError.getResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exceptionHandler(Exception ex){
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return apiError.getResponseEntity();
    }
}