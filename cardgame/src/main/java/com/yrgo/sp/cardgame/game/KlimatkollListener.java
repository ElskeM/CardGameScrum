package com.yrgo.sp.cardgame.game;

/**
 * An interface for listening to happenings in the game Klimatkoll that does not
 * trigger from a specific action.
 * 
 * @author simon
 */

public interface KlimatkollListener {
	
	/**
	 * Method to keep track if the game is draw
	 * @param game
	 */
	public void gameIsDraw(Game g);
	
	
	/**
	 * method to keep track of the timer 
	 * @param game
	 */
	public void timerRunOut(Game g);
	
	
	/**
	 * method that keeps track of the timer, if it runs out 3 times, the player loses.
	 * @param game
	 * @param player
	 */
	public void walkover(Game g, Player p);
}
