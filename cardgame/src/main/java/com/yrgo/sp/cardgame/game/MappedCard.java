package com.yrgo.sp.cardgame.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yrgo.sp.cardgame.domain.Card;
import com.yrgo.sp.cardgame.domain.Category;

/**
 * @author ptemrz
 * MappedCard Entity
 */
public class MappedCard implements Comparable<MappedCard> {

	@JsonIgnore
	Card card;
	Long gameId;

	/**
	 * Constructor for the MappedCard class
	 * @param card
	 * @param id
	 */
	public MappedCard(Card c, Long id) {
		this.card = c;
		this.gameId = id;
	}

	/**
	 * CompareTo method
	 */
	@Override
	public int compareTo(MappedCard o) {
		return card.compareTo(o.getCard());
	}
	
	/**
	 * ToString Method
	 */
	@Override
	public String toString() {
		return card.toString();
	}
	
	// Getter and Setter methods
	
	public Long getId() {
		return gameId;
	}

	public Card getCard() {
		return card;
	}


	public String getTitle() {
		return card.getTitle();
	}

	public String getDescription() {
		return card.getDescription();
	}

	public Category getCategory() {
		return card.getCategory();
	}

	public Integer getScore() {
		return card.getScore();
	}

	public String getFrontImage() {
		return card.getFrontImage();
	}

	public Integer getFrequence() {
		return card.getFrequence();
	}

	public String getAuthor() {
		return card.getAuthor();
	}

	public String getSubtitle() {
		return card.getSubtitle();
	}

	public String getExtraInfo() {
		return card.getExtraInfo();
	}

	public String getBackImage() {
		return card.getBackImage();
	}

}
