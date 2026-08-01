package com.mrshaikhmuhammad.ridelink.error;

import org.springframework.dao.*;
import org.springframework.http.*;
import org.springframework.messaging.*;
import org.springframework.security.access.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.messaging.handler.annotation.*;

@ControllerAdvice
public class GlobalMessageExceptionHandler {

    private static final String ERROR_QUEUE = "/queue/error";

    @MessageExceptionHandler(AccessDeniedException.class)
    @SendToUser(value = ERROR_QUEUE, broadcast = false)
    public ApiError handleAccessDenied(AccessDeniedException ex) {
        return new ApiError(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @MessageExceptionHandler(UsernameNotFoundException.class)
    @SendToUser(value = ERROR_QUEUE, broadcast = false)
    public ApiError handleUsernameNotFound(UsernameNotFoundException ex) {
        return new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @MessageExceptionHandler(IllegalArgumentException.class)
    @SendToUser(value = ERROR_QUEUE, broadcast = false)
    public ApiError handleIllegalArgument(IllegalArgumentException ex) {
        return new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @MessageExceptionHandler(NullPointerException.class)
    @SendToUser(value = ERROR_QUEUE, broadcast = false)
    public ApiError handleNullPointer(NullPointerException ex) {
        return new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @MessageExceptionHandler(DataAccessException.class)
    @SendToUser(value = ERROR_QUEUE, broadcast = false)
    public ApiError handleDataAccess(DataAccessException ex) {
        return new ApiError(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @MessageExceptionHandler(MessagingException.class)
    @SendToUser(value = ERROR_QUEUE, broadcast = false)
    public ApiError handleMessaging(MessagingException ex) {
        return new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser(value = ERROR_QUEUE, broadcast = false)
    public ApiError handleGeneric(Exception ex) {
        return new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}