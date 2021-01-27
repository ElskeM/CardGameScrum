package com.yrgo.sp.cardgame.chat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pontus
 * Chat entity which contains a messagelist and a method to add a message to that list
 *
 */
public class Chat {
	
	private List<ChatMessage> messages;
	
	/**
	 * Constructor for the chat class, which instantiates an ArrayList for the chatmessages
	 */
	public Chat() {
		messages = new ArrayList<ChatMessage>();
	}
	
	
	/**
	 * Method to add a new chat message to the message ArrayList
	 * @param message
	 */
	public void addNewChatMessage(ChatMessage message) {
		messages.add(message);
	}

}
