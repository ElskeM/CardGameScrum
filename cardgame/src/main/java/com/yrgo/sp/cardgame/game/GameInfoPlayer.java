package com.yrgo.sp.cardgame.game;

/**
 * @author ptemrz
 * GameInfoPlayer entity, which creates gameinfodetails on a player level
 *
 */
public class GameInfoPlayer {
	
	private String name;
	private int wins;

	/**
	 * Constructor for gameinfoplayer class
	 * @param player
	 */
	public GameInfoPlayer(Player p) {
		this.name = p.getName();
		this.wins = p.getWins();
	}
	
	// Getter methods
	
	public String getName() {
		return this.name;
	}
	
	public int getWins() {
		return this.wins;
	}

}
