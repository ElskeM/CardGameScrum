package com.yrgo.sp.cardgame.game;

/**
 * @author ptemrz
 * InactivityListener interface to keep track of the question if the game has gone inactive
 */
public interface InactivityListener {
	/**
	 * keeps track of inactive games, so their data can be removed from the server
	 * @param gameId
	 */
	void hasGoneInactive(long gameId);
}
