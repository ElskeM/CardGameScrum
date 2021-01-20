package com.yrgo.sp.cardgame.chat;

import java.util.ArrayList;
import java.util.List;

public class Chat {
	
	private List<ChatMessage> messages;
	
	public Chat() {
		messages = new ArrayList<ChatMessage>();
	}
	
	public void addNewChatMessage(ChatMessage message) {
		messages.add(message);
	}

}
