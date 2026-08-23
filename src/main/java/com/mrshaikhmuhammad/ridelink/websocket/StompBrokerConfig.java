package com.mrshaikhmuhammad.ridelink.websocket;

import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class StompBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${websocket.transport-limit.message-size}")
    private int messageSizeLimit;
    @Value("${websocket.transport-limit.send-time}")
    private int sendTimeLimit;
    @Value("${websocket.transport-limit.buffer-size}")
    private int bufferSizeLimit;

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(messageSizeLimit);
        registry.setSendBufferSizeLimit(bufferSizeLimit);
        registry.setSendTimeLimit(sendTimeLimit);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins(allowedOrigins)
                .withSockJS();
    }
}