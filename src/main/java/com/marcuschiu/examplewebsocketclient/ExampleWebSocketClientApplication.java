package com.marcuschiu.examplewebsocketclient;

import com.marcuschiu.examplewebsocketclient.configuration.MyStompSessionHandler;
import com.marcuschiu.examplewebsocketclient.model.Greeting;
import com.marcuschiu.examplewebsocketclient.model.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ExampleWebSocketClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExampleWebSocketClientApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("*");
			}
		};
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("entered command-line-runner");

		// https://stackoverflow.com/questions/30413380/websocketstompclient-wont-connect-to-sockjs-endpoint

		WebSocketClient transport = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSession session = stompClient.connect("ws://localhost:8080/gs-guide-websocket", new MyStompSessionHandler()).get();

//		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
//		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//		stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
//		StompSession session = stompClient.connect("ws://localhost:8080/gs-guide-websocket", new MyStompSessionHandler()).get();

//
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
		Message msg = new Message();
		msg.setName("Marcus Chiu");
		session.send("/app/hello", msg);

		System.out.println("entering scanner");
		new Scanner(System.in).nextLine(); // Don't close immediately.
	}
}
