package com.yrgo.sp.cardgame.game;

public class Player {

	private String name;
	int guess;
	
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGuess(int guess) {
		this.guess = guess;
	}
	
	public int getGuess() {
		return guess;
	}
}
