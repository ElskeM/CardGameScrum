package com.yrgo.sp.cardgame.chat;

public class ChatMessage {
	
	private String name;
	private String message;
	
	public ChatMessage(String name, String message) {
		this.name = name;
		this.message= message;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return message;
	}

}
