package com.yrgo.sp.cardgame.game;

public class GameInfoPlayer {
	
	private String name;
	private int wins;

	public GameInfoPlayer(Player p) {
		this.name = p.getName();
		this.wins = p.getWins();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getWins() {
		return this.wins;
	}

}
