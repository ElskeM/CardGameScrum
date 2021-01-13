package com.yrgo.sp.cardgame.chat;

import java.util.HashMap;
import java.util.Map;


public class ChatService {
	
	private Map<Long,Chat> chats = new HashMap<>();
	
	public Chat createChat(long id) {
		Chat chat = new Chat(id);
		chats.put(id, chat);
		return chat;
	}
	
	public Chat findChatById (long id) {
		return chats.get(id);
	}
	
	

}
