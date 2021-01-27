package com.yrgo.sp.cardgame.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrgo.sp.cardgame.data.CardRepository;

/**
 * @author ptemrz, pontus, simon
 * GameService Entity which implements CardGameApi interface
 *
 */
@Service
public class GameService implements CardGameApi {
	
	@Autowired
	private CardRepository cardData;
	
	private Map<Long,Game> games = new HashMap<>();
	
	/**
	 * Implementation of the Create game method
	 * Checks if there are cards in the database, if not, a game can't be created.
	 * @param id
	 * @return Game
	 */
	@Override
	public Game createGame(long id) {
		Game game = new Game(id,2);
		if(game.getMinCards() > cardData.count()) {
			throw new IllegalStateException("Not enough cards in database to create this game!");
		}
		games.put(id, game);
		return game;
	}
	
	/**
	 * Implementation of the getGameById method. 
	 * @param Id
	 * @return Game of specified id
	 */
	@Override
	public Optional<Game> getGameById(long id) {
		return Optional.ofNullable(games.get(id));
	}
	
	/** @ToDo: method needs to be assessed! 
	 * implementation of the placecard method
	 * @param gameid
	 */
	@Override
	public void placeCard(long gameId) {
		Game game = games.get(gameId);
	}
	
	/**
	 * Method to fill a deck for a game
	 * @param gameId
	 */
	public void fillDeck(long gameId) {
		Game game = games.get(gameId);
		game.getDeck().fillDeck(cardData.findAll().stream().collect(Collectors.toSet()));
		
	}
}
