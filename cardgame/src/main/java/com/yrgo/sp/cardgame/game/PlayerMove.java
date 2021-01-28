package com.yrgo.sp.cardgame.game;

/**
 * @author pontus
 * Playermove entity
 */
public class PlayerMove {
	
	private String playerName;
	private long cardId;
	private int cardPosition;
	
	/**
	 * Empty constructor
	 */
	public PlayerMove() {}
	
	// Getter and Setter methods
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public long getCardId() {
		return cardId;
	}
	public void setCardId(long cardId) {
		this.cardId = cardId;
	}
	public int getCardPosition() {
		return cardPosition;
	}
	public void setCardPosition(int cardPosition) {
		this.cardPosition = cardPosition;
	}
	
	

}
