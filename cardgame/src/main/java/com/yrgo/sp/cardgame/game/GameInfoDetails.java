package com.yrgo.sp.cardgame.game;

import java.util.HashMap;

public class GameInfoDetails {
	private int matches;
	private HashMap<String, Integer> playerWins = new HashMap<>();

	public GameInfoDetails(Game g) {
		this.matches = g.getNumberOfGames();
		for(Player p: g.getPlayers()) {
			playerWins.put(p.getName(), p.getWins());
		}
	}
	
	public int getMatches() {
		return this.matches;
	}
	
	public HashMap<String, Integer> getPlayers(){
		return this.playerWins;
	}
}
