package com.sm.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
public class WebSocketConfig {
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new SocketHandler(), "/user");
	}
}
