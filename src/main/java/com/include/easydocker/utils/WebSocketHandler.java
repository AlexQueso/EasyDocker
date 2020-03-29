package com.include.easydocker.utils;

import com.include.easydocker.session.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private UsersSession usersSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        usersSession.setWebSocketSession(session);
    }
}
