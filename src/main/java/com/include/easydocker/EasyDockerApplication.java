package com.include.easydocker;

import com.include.easydocker.utils.WebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@EnableWebSocket
public class EasyDockerApplication implements WebSocketConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(EasyDockerApplication.class, args);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(new WebSocketHandler(), "/ws");
	}
}
