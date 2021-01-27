package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ptemrz, simon
 * GameInfoDetails entity 
 *
 */
public class GameInfoDetails {
	private int matches;
	private List<GameInfoPlayer> playerWins = new ArrayList<GameInfoPlayer>();

	/**
	 * Constructor for the GameInfoDetail class
	 * @param game
	 */
	public GameInfoDetails(Game g) {
		this.matches = g.getNumberOfGames();
		for(Player p: g.getPlayers()) {
			playerWins.add(new GameInfoPlayer(p));
		}
	}
	
	// Getter methods
	
	public int getMatches() {
		return this.matches;
	}
	
	public List<GameInfoPlayer> getPlayers(){
		return this.playerWins;
	}
}
