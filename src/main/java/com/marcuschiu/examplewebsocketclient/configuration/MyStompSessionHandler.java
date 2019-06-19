package com.marcuschiu.examplewebsocketclient.configuration;

import com.marcuschiu.examplewebsocketclient.model.Greeting;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class MyStompSessionHandler implements StompSessionHandler {
    @Override
    public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        System.out.println("connected to websocket server");
        stompSession.subscribe("/topic/greetings", this);
    }

    @Override
    public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
        System.out.println(throwable.toString());
        System.out.println("failed handleException");
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        System.out.println(throwable.toString());
        System.out.println("failed handleTransportError");
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return Greeting.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        Greeting msg = (Greeting) o;
        System.out.println("Received: " + msg.getContent());
    }
}
