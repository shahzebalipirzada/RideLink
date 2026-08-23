package com.mrshaikhmuhammad.ridelink.service;

import com.mrshaikhmuhammad.ridelink.entity.*;
import com.mrshaikhmuhammad.ridelink.entity.type.ConversationType;
import com.mrshaikhmuhammad.ridelink.repository.ConversationRepository;
import com.mrshaikhmuhammad.ridelink.repository.MessageRepository;
import org.springframework.stereotype.*;
import org.bson.types.*;
import lombok.*;


import java.time.*;
import java.util.*;
import java.util.stream.*;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    Message saveMessageAndUpdateConversation(Conversation conversation, User sender, String content) {
        Message message = messageRepository.save(
                Message.builder()
                        .conversation(conversation)
                        .sender(sender)
                        .senderName(sender.getName())
                        .content(content)
                        .timestamp(Instant.now())
                        .build()
        );

        conversation.setLastMessage(
                MessagePreview.builder()
                        .messageId(message.getId())
                        .senderName(sender.getName())
                        .contentPreview(truncate(content, 100))
                        .timestamp(message.getTimestamp())
                        .build()
        );
        conversationRepository.save(conversation);

        return message;
    }

    String truncate(String content, int maxLen) {
        if (content == null || content.length() <= maxLen) return content;
        return content.substring(0, maxLen) + "…";
    }

    String createDirectConversationKey(ObjectId a, ObjectId b) {
        return Stream.of(a.toHexString(), b.toHexString())
                .sorted()
                .collect(Collectors.joining("_"));
    }

    Conversation createDirectConversation(User sender, User receiver, String participantsKey) {
        return conversationRepository.save(
                Conversation.builder()
                        .type(ConversationType.DIRECT)
                        .participants(List.of(sender, receiver))
                        .directConversationKey(participantsKey)
                        .createdAt(Instant.now())
                        .build());
    }
}