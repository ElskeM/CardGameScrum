package com.yrgo.sp.cardgame.chat;

import java.util.HashMap;
import java.util.Map;


/**
 * @author pontus
 * Chatservice entity
 *
 */
public class ChatService {
	
	private Map<Long,Chat> chats = new HashMap<>();
	
	/**
	 * Method to create a chat, which takes an id as parameter
	 * @param id
	 * @return chat
	 */
	public Chat createChat(long id) {
		Chat chat = new Chat();
		chats.put(id, chat);
		return chat;
	}
	
	/**
	 * Method to find a chat by id
	 * @param id
	 * @return found chat
	 */
	public Chat findChatById (long id) {
		return chats.get(id);
	}
	
	

}
