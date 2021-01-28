package com.yrgo.sp.cardgame.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.yrgo.sp.cardgame.domain.Card;

/**
 * @author ptemrz
 * CardList Entity
 *
 */
@JsonRootName(value = "cards")
public class CardList {
	private List<Card> cards;

	/**
	 * Empty constructor
	 */
	public CardList() {
	}

	/**
	 * Constructor for the cardlist class
	 * @param cards
	 */
	public CardList(List<Card> cards) {
		this.cards = cards;
	}

	// Getter and Setter methods
	
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
