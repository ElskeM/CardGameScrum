package com.yrgo.sp.cardgame.game;

/**
 * @author simon
 * Interface that functions as a listener in the game
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
