package com.yrgo.sp.cardgame.game;

import java.util.Random;
import java.util.Set;

import com.yrgo.sp.cardgame.domain.Card;

public class Deck {
	
	private Set<Card> cards;

	public Card draw() {
		// TODO Auto-generated method stub
		return new Card("testcard"+new Random().nextInt(50),1000);
	}

}
