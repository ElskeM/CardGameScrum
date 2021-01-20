package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;

public class GameInfoDetails {
	private int matches;
	private List<GameInfoPlayer> playerWins = new ArrayList<GameInfoPlayer>();

	public GameInfoDetails(Game g) {
		this.matches = g.getNumberOfGames();
		for(Player p: g.getPlayers()) {
			playerWins.add(new GameInfoPlayer(p));
		}
	}
	
	public int getMatches() {
		return this.matches;
	}
	
	public List<GameInfoPlayer> getPlayers(){
		return this.playerWins;
	}
}
