package com.yrgo.sp.cardgame.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;


public class ChatWSController {
	
	@Autowired
	ChatService chatService;
	
	@MessageMapping("/chatmessage/{id}")
	@SendTo("/chat/{id}")
	public ChatMessage newChatMessage(ChatMessage message, @DestinationVariable long id) {
		//Chat chat = chatService.findChatById(id);
		//chat.addNewChatMessage(message);
		return message;
	
	}

}



