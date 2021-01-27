package com.yrgo.sp.cardgame.chat;

/**
 * @author pontus
 * Chatmessage entity
 */
public class ChatMessage {
	
	private String name;
	private String message;
	private String color;
	
	/**
	 * Constructor for the chatmessage class
	 * @param name
	 * @param message
	 * @param color
	 */
	public ChatMessage(String name, String message, String color) {
		this.name = name;
		this.message= message;
		this.color = color;
	}
	
	
	/**
	 * Empty constructor
	 */
	public ChatMessage() {
		
	}
	
	// Getter and Setter methods
	
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
	
	/**
	 * ToString method
	 */
	@Override
	public String toString() {
		return this.name + " " + this.message;
	}

}
