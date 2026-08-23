package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.dto.response.*;
import com.mrshaikhmuhammad.ridelink.dto.request.*;
import com.mrshaikhmuhammad.ridelink.entity.type.*;
import com.mrshaikhmuhammad.ridelink.repository.*;
import com.mrshaikhmuhammad.ridelink.security.*;
import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.entity.User;

import org.springframework.security.core.userdetails.*;
import org.springframework.transaction.annotation.*;
import org.springframework.security.access.*;
import org.springframework.messaging.simp.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.bson.types.*;
import lombok.*;

import java.security.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final AuthUtil authUtil;
    private final MessageUtil messageUtil;

    @Transactional
    public void sendMessageToPerson(Principal principal, MessageRequestDto request) {
        // 1 Check User Authenticated
        if (principal == null) {
            throw new AccessDeniedException("Unauthenticated WebSocket session");
        }

        // 2 Check Sender & Receiver Exist
        User sender = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));
        User receiver = userRepository.findByUsername(request.receiver())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.receiver()));

        // 3 Check is user sending message to himself
        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException("Cannot send a message to yourself");
        }

        //4. If Conversation not exist CREATE One
        Conversation conversation = null;
        if(StringUtils.hasText(request.conversationId())){
            ObjectId conversationId = new ObjectId(request.conversationId());
            conversation = conversationRepository.findById(conversationId).orElse(null);
        }
        if(conversation == null){
            String directConversationKey = messageUtil.createDirectConversationKey(sender.getId(), receiver.getId());
            conversation = conversationRepository.findByDirectConversationKey(directConversationKey)
                    .orElseGet(() -> messageUtil.createDirectConversation(sender, receiver, directConversationKey));
        }

        // 5. Verify Sender & Receiver part of conversation
        if (conversation.getType() != ConversationType.DIRECT) {
            throw new AccessDeniedException("Access denied: not a direct conversation");
        }

        Set<ObjectId> participantIds = conversation.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        if (!participantIds.containsAll(Set.of(sender.getId(), receiver.getId()))) {
            throw new AccessDeniedException("Access denied: only members can send messages in this chat");
        }

        // 6. Store Message in DB
        Message message = messageUtil.saveMessageAndUpdateConversation(conversation, sender, request.content());

        //7. Send Message to Socket
        MessageResponseDto payload = new MessageResponseDto(conversation.getId().toString(), principal.getName(), request.content());
        simpMessagingTemplate.convertAndSendToUser(request.receiver(), "/queue/messages", payload);
    }

    @Transactional
    public void sendMessageToGroup(Principal principal, MessageRequestDto request) {
        // 1. Check User is Authenticated
        if (principal == null) {
            throw new AccessDeniedException("Unauthenticated WebSocket session");
        }

        // 2. Check Ride & Sender exist in DB
        ObjectId rideId = new ObjectId(request.rideId());
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found: " + rideId));
        User sender = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + principal.getName()));

        // 3. Check Sender is Member of conversation
        List<User> participants = new ArrayList<>();
        participants.add(ride.getCreator());
        participants.addAll(ride.getJoiners());

        boolean isMember = participants.stream()
                .anyMatch(p -> p.getId().equals(sender.getId()));
        if (!isMember) {
            throw new AccessDeniedException("Access denied: only ride members can send messages in this chat");
        }

        // 4. IF conversation is empty THEN CREATE it
        Conversation conversation = ride.getConversation();
        if(conversation == null){
            conversation = conversationRepository.save(
                    Conversation.builder()
                            .type(ConversationType.GROUP)
                            .participants(participants)
                            .createdAt(Instant.now())
                            .build()
            );

            ride.setConversation(conversation);
            rideRepository.save(ride);
        }

        // 5. SYNC to conversation & RIDE MEMBERS
        Set<ObjectId> conversationMemberIds = conversation.getParticipants().stream()
                .map(User::getId).collect(Collectors.toSet());
        Set<ObjectId> rideMembersIds = participants.stream()
                .map(User::getId).collect(Collectors.toSet());

        if (!conversationMemberIds.equals(rideMembersIds)) {
            conversation.setParticipants(participants);
            conversationRepository.save(conversation);
        }

        // 6. Verify Conversation Type is correct
        if (conversation.getType() != ConversationType.GROUP) {
            throw new AccessDeniedException("Access denied: not a group conversation");
        }

        // 7. SAVE message in DB
        Message message = messageUtil.saveMessageAndUpdateConversation(conversation, sender, request.content());

        // 8. SEND message to GROUP
        MessageResponseDto payload = new MessageResponseDto(conversation.getId().toString(), principal.getName(), request.content());
        simpMessagingTemplate.convertAndSend("/topic/" + request.rideId(), payload);
    }


    public List<Conversation> loadChat() {
        User sender = authUtil.getAuthenticatedUser();
        return conversationRepository.findByParticipantsContains(sender);
    }

    public List<Message> loadConversation(MessageRequestDto request) {
        User sender = authUtil.getAuthenticatedUser();
        Conversation conversation = conversationRepository.findById(new ObjectId(request.conversationId()))
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found: no conversation exists with this ID " + request.conversationId()));

        Set<ObjectId> participantIds = conversation.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        if(!participantIds.contains(sender.getId())){
            throw new AccessDeniedException("Access denied: members can access messages");
        }

        return messageRepository.findByConversation(conversation);
    }
}