package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.service.MessageService;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.*;
import org.springframework.stereotype.*;
import org.springframework.http.*;
import lombok.*;

import java.security.Principal;

@Controller
@MessageMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/send/person")
    public void sendMessageToPerson(
            @RequestBody MessageRequestDto request,
            Principal principal
    ){
        messageService.sendMessageToPerson(principal,  request);
    }

    @MessageMapping("/send/group")
    public void sendMessageToGroup(
            @RequestBody MessageRequestDto request,
            Principal principal
    ){
        messageService.sendMessageToGroup(principal, request);
    }

    @MessageMapping("/load")
    public void loadMessages(Principal principal){
        messageService.loadMessages(principal);
    }
}