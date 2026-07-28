package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.request.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.*;
import org.springframework.stereotype.*;
import org.springframework.http.*;
import lombok.*;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/send/person/{username}")
    public ResponseEntity<?> sendMessageToPerson(
            @DestinationVariable String username,
            @RequestBody MessageRequestDto request
    ){
        messageService.sendMessageToPerson(username, request);
    }

    @MessageMapping("/send/group/{ride-id}")
    public ResponseEntity<?> sendMessageToGroup(
            @DestinationVariable String rideId,
            @RequestBody MessageRequestDto request
    ){
        messageService.sendMessageToGroup(rideId, request);
    }

}
