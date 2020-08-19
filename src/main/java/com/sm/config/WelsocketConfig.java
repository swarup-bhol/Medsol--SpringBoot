package com.sm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WelsocketConfig implements WebSocketMessageBrokerConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
		stompEndpointRegistry.addEndpoint("/ws")
				.setAllowedOrigins("*")
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic", "/user");
		registry.setApplicationDestinationPrefixes("/app");
	}
}
