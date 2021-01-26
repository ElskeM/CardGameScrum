package com.yrgo.sp.cardgame.game;

public interface KlimatkollListener {
	public void gameIsDraw(Game g);
	public void timerRunOut(Game g);
	public void walkover(Game g, Player p);
}
