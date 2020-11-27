package com.yrgo.sp.cardgame.game;

import org.springframework.stereotype.Service;

@Service
public class GameService implements CardGameApi {

	@Override
	public Game createGame() {
		return new Game();
	}

}
