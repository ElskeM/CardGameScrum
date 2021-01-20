package com.yrgo.sp.cardgame.game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrgo.sp.cardgame.data.CardRepository;

@Service
public class GameService implements CardGameApi {
	
	@Autowired
	private CardRepository cardData;
	
	private Map<Long,Game> games = new HashMap<>();
	
	@Override
	public Game createGame(long id) {
		Game game = new Game(id,2);
		//Deck d = new Deck("Default","Admin",cardData.findAll().stream().collect(Collectors.toSet()));
		//game.setDeck(d);
		games.put(id, game);
		return game;
	}
	
	@Override
	public Game getGameById(long id) {
		return games.get(id);
	}
	
	@Override
	public void placeCard(long gameId) {
		Game game = games.get(gameId);
//		game.giveCardTo(null);
	}
	
	public void fillDeck(long gameId) {
		Game game = games.get(gameId);
		game.getDeck().fillDeck(cardData.findAll().stream().collect(Collectors.toSet()));
		
	}
}
