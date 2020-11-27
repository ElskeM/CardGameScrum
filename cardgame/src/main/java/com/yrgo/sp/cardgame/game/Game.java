package com.yrgo.sp.cardgame.game;

import java.util.Random;

public class Game {
	
	private long id;
	private int number = new Random().nextInt(10);
	private int userNumber;
	
	public Game() {
	//	this.id = UUID.randomUUID().toString();
	//	this.id = id;
	}
	
	public String whoWins(int number, int userNumber) {
		if(number > userNumber) {
			return "YOU LOSE!";
		} else {
			return "YOU WIN!";
		}
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
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
