package com.yrgo.sp.cardgame.chat;

import java.util.ArrayList;
import java.util.List;

public class Chat {
	
	private long id;
	private List<ChatMessage> messages;
	
	public Chat(long id) {
		this.id = id;
		messages = new ArrayList<ChatMessage>();
	}
	
	public void addNewChatMessage(ChatMessage message) {
		messages.add(message);
	}

}
