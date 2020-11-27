package com.yrgo.sp.cardgame.game;

import java.io.Serializable;
import java.util.UUID;

public class Game{
	
	private String id;
	private int number = 10;
	private int userNumber;
	
	public Game(String id) {
	//	this.id = UUID.randomUUID().toString();
		this.id = id;
	}
	
	public String whoWins(int number, int userNumber) {
		if(number > userNumber) {
			return "YOU LOSE!";
		} else {
			return "YOU WIN!";
		}
	}
	
	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getUserNumber() {
		return userNumber;
	}
	

}
