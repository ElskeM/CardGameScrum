package com.yrgo.sp.cardgame.game;

import com.yrgo.sp.cardgame.domain.Card;

public interface CardGameApi {
	Game createGame(long id);
	Game getGameById(long id);
	void placeCard(long gameId);
}
