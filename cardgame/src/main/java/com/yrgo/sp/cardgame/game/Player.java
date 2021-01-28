package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ptemrz, pontus, simon
 * Player entity for the game context
 */
public class Player {

	private String name;
	private boolean turn = false;
	private List<MappedCard> hand;
	private int wins = 0;
	private int missedTurns=0;


	/**
	 * Constructor for the game player class
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.hand = new ArrayList<MappedCard>();
	}

	/** Method to add a card to the players hand
	 * @param card
	 */
	public void addCardToHand(MappedCard card) {
		hand.add(card);
	}
	
	// Getter and Setter methods
	
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
	public void addMissedTurn() {
		missedTurns++;
	}
	public int getMissedTurns() {
		return missedTurns;
	}
	public void resetMissedTurns() {
		missedTurns=0;
	}
}
