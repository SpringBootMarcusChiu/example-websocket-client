package com.marcuschiu.examplewebsocketclient;

import com.marcuschiu.examplewebsocketclient.configuration.MyStompSessionHandler;
import com.marcuschiu.examplewebsocketclient.model.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootApplication
public class ExampleWebSocketClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExampleWebSocketClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("entered command-line-runner");

		// https://stackoverflow.com/questions/30413380/websocketstompclient-wont-connect-to-sockjs-endpoint

		// OPTION 1
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSession session = stompClient.connect("ws://localhost:8080/gs-guide-websocket", new MyStompSessionHandler()).get();

		// OPTION 2
//		List<Transport> transports = new ArrayList<>(1);
//		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//		WebSocketClient transport = new SockJsClient(transports);
//		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
//		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//		StompSession session = stompClient.connect("ws://localhost:8080/gs-guide-websocket", new MyStompSessionHandler()).get();
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

		System.out.println("Sending: Marcus Chiu");
		Message msg = new Message();
		msg.setName("Marcus Chiu");
		session.send("/app/hello", msg);
	}
}
