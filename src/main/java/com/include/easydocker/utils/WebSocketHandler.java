package com.include.easydocker.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class WebSocketHandler extends TextWebSocketHandler {

    public static WebSocketSession webSocketSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSession = session;
    }

    public static void sendMessage(String msg){
        while (true){
            try {
                webSocketSession.sendMessage(new TextMessage(msg));
                break;
            } catch (Exception ignored) {}
        }
    }
}
