package com.mrshaikhmuhammad.ridelink.controller;

import com.mrshaikhmuhammad.ridelink.dto.request.MessageRequestDto;
import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.service.MessageService;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import lombok.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/load-conversation")
    public ResponseEntity<?> loadConversation(@RequestBody MessageRequestDto request){
        List<Message> conversation = messageService.loadConversation(request);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    @MessageMapping("/load-chat")
    public ResponseEntity<?> loadChat(){
        List<Conversation> chat = messageService.loadChat();
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
}