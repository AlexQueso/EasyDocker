package com.include.easydocker.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.include.easydocker.session.UsersSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import static java.lang.Thread.sleep;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    public static WebSocketSession webSocketSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //usersSession.setWebSocketSession(session);
        this.webSocketSession = session;
//        ObjectMapper json = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

//        Thread t = new Thread(() -> {
//            while (true)
//                try {
//                    String msgJson =  json.writeValueAsString(Math.random());
//                    session.sendMessage(new TextMessage(msgJson));
//                    System.out.println("Sent message '" + msgJson + "' to client " + session.getId());
//                    sleep(1000);
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//        });
//        t.start();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(message);
    }
}
