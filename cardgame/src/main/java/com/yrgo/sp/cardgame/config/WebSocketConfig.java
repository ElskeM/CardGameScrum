package com.yrgo.sp.cardgame.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author pontus
 * Entity for the configuration of the websocket
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * Method to configure the messagebroker
	 * @param MessageBrokerRegistry 
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/cardgame");
		config.setApplicationDestinationPrefixes("/app");
		
	}
	
	/**
	 * Method to register StompEndpoints
	 * @param StompEndpointRegistry 
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/gs-guide-websocket")
		.setAllowedOrigins("http://localhost:8081")
		.withSockJS();
		
	}
	
	
}
