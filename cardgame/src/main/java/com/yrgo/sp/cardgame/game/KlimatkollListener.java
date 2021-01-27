package com.yrgo.sp.cardgame.game;

/**
 * An interface for listening to happenings in the game Klimatkoll that does not
 * trigger from a specific action.
 * 
 * @author simon
 *
 */
public interface KlimatkollListener {
	public void gameIsDraw(Game g);

	public void timerRunOut(Game g);

	public void walkover(Game g, Player p);
}
