package com.yrgo.sp.cardgame.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author pontus
 * ChatWSController entity which takes care of the chatmessage mapping for the client project 
 *
 */
@Controller
public class ChatWSController {

	
	/**
	 * Method that returns a new chatmessage to a chat
	 * @param message
	 * @return message
	 */
	@MessageMapping("/chatmessage/{id}")
	@SendTo("/cardgame/chat/{id}")
	public ChatMessage newChatMessage(ChatMessage message) {
		return message;
	
	}

}



