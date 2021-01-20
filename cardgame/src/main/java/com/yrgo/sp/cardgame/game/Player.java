package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private boolean turn = false;
	private List<MappedCard> hand;
	private int wins = 0;

	public void addCardToHand(MappedCard card) {
		hand.add(card);
	}

	public Player(String name) {
		this.name = name;
		this.hand = new ArrayList<MappedCard>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public void setHand(List<MappedCard> cards) {
		this.hand = new ArrayList<>(cards);
	}

	public List<MappedCard> getHand() {
		return hand;
	}

	public int getWins() {
		return wins;
	}

	public void addWin() {
		wins++;
	}
}
