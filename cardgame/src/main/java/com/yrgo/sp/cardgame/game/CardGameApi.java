package com.yrgo.sp.cardgame.game;

public interface CardGameApi {
	Game createGame(long id);
	Game getGameById(long id);
	void placeCard(long gameId);
}
