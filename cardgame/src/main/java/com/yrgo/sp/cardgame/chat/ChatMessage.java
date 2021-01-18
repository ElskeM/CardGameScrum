package com.yrgo.sp.cardgame.chat;

public class ChatMessage {
	
	private String name;
	private String message;
	private String color;
	
	public ChatMessage(String name, String message, String color) {
		this.name = name;
		this.message= message;
		this.color = color;
	}
	
	public ChatMessage() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.message;
	}

}
