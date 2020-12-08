package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;

import com.yrgo.sp.cardgame.domain.Card;

public class Player {

	private String name;
	int guess;
	private List<Card> hand;

	
	public void addCardToHand(Card card) {
		hand.add(card);
	}
	
	
	public Player(String name) {
		this.name = name;
		this.hand = new ArrayList<Card>();
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
	
	public void setHand(List<Card> cards) {
		this.hand = new ArrayList<>(cards);
	}
	
	public List<Card> getHand() {
		return hand;
	}
}
