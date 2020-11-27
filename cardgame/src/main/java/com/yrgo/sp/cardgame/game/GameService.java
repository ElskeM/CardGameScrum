package com.yrgo.sp.cardgame.game;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GameService implements CardGameApi {

	@Override
	public Game createGame() {
		return new Game("testgame" + new Random(10).nextInt());
	}

}
