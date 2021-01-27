package com.yrgo.sp.cardgame.game;

import java.util.Optional;

/**
 * @author ptemrz
 * CardGameAPI interface which is implemented in the GameService class.
 */
public interface CardGameApi {
	
	/**
	 * Method to create a game
	 * @param id
	 * @return Game
	 */
	Game createGame(long id);
	
	/**
	 * Method to find a game by its id
	 * @param id
	 * @return found game
	 */
	Optional<Game> getGameById(long id);
	
	/**@ToDo: method needs to be assessed! 
	 * Method to place a card
	 * @param gameId
	 */
	void placeCard(long gameId);
}
