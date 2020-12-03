package com.yrgo.sp.cardgame.game;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.domain.Deck;

@Service
public class GameService implements CardGameApi {
	
	@Autowired
	private CardRepository cardData;

	@Override
	public Game createGame() {
		Game game = new Game();
		Deck d = new Deck("Default","Admin",cardData.findAll().stream().collect(Collectors.toSet()));
		game.setDeck(d);
		return game;
	}

}
