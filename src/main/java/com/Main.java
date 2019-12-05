package com;

import com.configuration.MyStompSessionHandler;
import com.model.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) throws Exception {
		// use wss:// for secure websockets
		StompSession session = generateWebSocketSession("ws://localhost:8080/gs-guide-websocket");

		System.out.println("press any key to send Broadcast Message (all users receive response message)");
		System.in.read();
		session.send("/app/hello", new Message("Marcus Chiu"));

		System.out.println("press any key to send Only-Me-Message (only this user receives response message)");
		System.in.read();
		session.send("/app/hello/specific-user", new Message("Marcus Chiu"));

		// wait for last message
		Thread.sleep(5000);
		session.disconnect();
	}

	private static StompSession generateWebSocketSession(String url) throws ExecutionException, InterruptedException {
		// https://stackoverflow.com/questions/30413380/websocketstompclient-wont-connect-to-sockjs-endpoint
		StompSession session;

		// OPTION 1
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		session = stompClient.connect(url, new MyStompSessionHandler()).get();

		// OPTION 2
//		List<Transport> transports = new ArrayList<>(1);
//		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//		WebSocketClient transport = new SockJsClient(transports);
//		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
//		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//		session = stompClient.connect("ws://localhost:8080/gs-guide-websocket", new MyStompSessionHandler()).get();
//		session.subscribe("/topic/greetings", new StompFrameHandler() {
//			@Override
//			public Type getPayloadType(StompHeaders headers) {
//				return Greeting.class;
//			}
//
//			@Override
//			public void handleFrame(StompHeaders headers, Object o) {
//				System.out.println("got something");
//				Greeting msg = (Greeting) o;
//				System.out.println("Received : " + msg.getContent());
//			}
//		});

		return session;
	}
}
