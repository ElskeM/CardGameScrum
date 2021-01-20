package com.yrgo.sp.cardgame.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWSController {

	
	@MessageMapping("/chatmessage/{id}")
	@SendTo("/cardgame/chat/{id}")
	public ChatMessage newChatMessage(ChatMessage message) {
		return message;
	
	}

}



