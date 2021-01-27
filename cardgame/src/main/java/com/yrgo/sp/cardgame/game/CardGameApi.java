package com.yrgo.sp.cardgame.game;

import java.util.Optional;

public interface CardGameApi {
	Game createGame(long id);
	Optional<Game> getGameById(long id);
	void placeCard(long gameId);
}
