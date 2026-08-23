package com.mrshaikhmuhammad.ridelink.websocket;

import com.mrshaikhmuhammad.ridelink.service.MessageService;
import com.mrshaikhmuhammad.ridelink.dto.request.MessageRequestDto;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;
import lombok.*;

import java.security.Principal;

@Controller
@MessageMapping("/message")
@RequiredArgsConstructor
public class StompController {

    private final MessageService messageService;

    @MessageMapping("/send/person")
    public void sendMessageToPerson(
            @Payload MessageRequestDto request,
            Principal principal
    ){
        messageService.sendMessageToPerson(principal,  request);
    }

    @MessageMapping("/send/group")
    public void sendMessageToGroup(
            @Payload MessageRequestDto request,
            Principal principal
    ){
        messageService.sendMessageToGroup(principal, request);
    }
}