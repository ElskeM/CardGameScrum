package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.yrgo.sp.cardgame.domain.Card;

/**
 * A deck of face down cards. This deck is in play and is not managed by an ORM.
 * It is meant to be destroyed when the game is over.
 * 
 * Face down cards cannot be viewed until they're drawn from the deck (and implicitely flipped)
 * @author ptemrz
 *
 */
public class Deck {

	private Set<MappedCard> cards = new HashSet<>();

	/**
	 * method to draw a card from the deck which is used in the game
	 * @return a random card from the deck
	 */
	public MappedCard draw() {
		List<MappedCard> cardlist = this.cards.stream().collect(Collectors.toList());
		MappedCard random = cardlist.remove(new Random().nextInt(cardlist.size()));
		this.cards = cardlist.stream().collect(Collectors.toSet());
		return random;
	}

	/**
	 * Method which fills the deck with a set of cards and shuffles them
	 * @param set of Cards
	 */
	public void fillDeck(Set<Card> set) {
		List<Card> list = new ArrayList<Card>(set);
		Collections.shuffle(list);
		long id = 0;
		for (Card c : list) {
			cards.add(new MappedCard(c, ++id));
		}
	}
}