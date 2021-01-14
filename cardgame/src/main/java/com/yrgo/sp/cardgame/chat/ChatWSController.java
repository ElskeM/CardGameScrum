package com.yrgo.sp.cardgame.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWSController {
	
	//@Autowired
	//ChatService chatService;
	
	@MessageMapping("/chatmessage/{id}")
	@SendTo("/cardgame/chat/{id}")
	public ChatMessage newChatMessage(ChatMessage message) {
		System.out.println("Nu är det nån som skickar meddelanden här");
		//Chat chat = chatService.findChatById(id);
		//chat.addNewChatMessage(message);
		return message;
	
	}

}



