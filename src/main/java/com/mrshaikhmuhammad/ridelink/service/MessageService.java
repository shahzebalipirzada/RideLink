package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.dto.response.MessageResponseDto;
import com.mrshaikhmuhammad.ridelink.entity.Ride;
import com.mrshaikhmuhammad.ridelink.entity.User;
import com.mrshaikhmuhammad.ridelink.repository.RideRepository;
import com.mrshaikhmuhammad.ridelink.repository.UserRepository;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.security.access.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.messaging.simp.*;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    public void sendMessageToPerson(Principal principal, MessageRequestDto request) {
        if (principal == null) {
            throw new AccessDeniedException("Unauthenticated WebSocket session");
        }
        String sender = principal.getName();

        User receiver = userRepository.findByUsername(request.receiver())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.receiver()));

        MessageResponseDto payload = new MessageResponseDto(sender, request.content());
        simpMessagingTemplate.convertAndSendToUser(request.receiver(), "/queue/messages", payload);
    }

    public void sendMessageToGroup(Principal principal, MessageRequestDto request) {
        if (principal == null) {
            throw new AccessDeniedException("Unauthenticated WebSocket session");
        }
        String sender = principal.getName();
        ObjectId rideId = new ObjectId(request.receiver());

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new UsernameNotFoundException("Ride not found: " + request.receiver()));

        MessageResponseDto payload = new MessageResponseDto(sender, request.content());
        simpMessagingTemplate.convertAndSend("/topic/" + rideId, payload);
    }


//    public void loadMessages(Principal principal) {
//        if (principal == null) {
//            throw new AccessDeniedException("Unauthenticated WebSocket session");
//        }
//        String username = principal.getName();
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//
//    }
}