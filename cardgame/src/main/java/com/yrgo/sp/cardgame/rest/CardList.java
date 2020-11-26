package com.yrgo.sp.cardgame.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.yrgo.sp.cardgame.domain.Card;

@JsonRootName(value = "cards")
public class CardList {
	private List<Card> cards;

	public CardList() {
	}

	public CardList(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
