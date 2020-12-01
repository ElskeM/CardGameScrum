package com.yrgo.sp.cardgame.game;

import com.yrgo.sp.cardgame.domain.Card;

public class Player {

	private String name;
	int guess;
	private Card card;
	
	
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
	
	public void setCard(Card card) {
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
}
