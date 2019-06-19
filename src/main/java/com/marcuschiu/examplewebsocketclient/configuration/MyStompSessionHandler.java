package com.marcuschiu.examplewebsocketclient.configuration;

import com.marcuschiu.examplewebsocketclient.model.Greeting;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        System.out.println("connected to websocket server");
        stompSession.subscribe("/topic/greetings", this);
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        Greeting msg = (Greeting) o;
        System.out.println("Received : " + msg.getContent());
    }
}
