package com.yrgo.sp.cardgame.domain;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


/**
 * A deck of cards that can be stored in a database.
 * @author ptemrz
 *
 */
@Entity
public class Deck {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The name of this deck
	 */
	@Column(unique = true, nullable = false)
	private String name;

	/**
	 * Who created this deck? Default: "Admin"
	 */
	@Column(columnDefinition = "varchar(255) default 'Admin'")
	private String creator;

	/**
	 * Is this deck publicly visible, or only for the creator?
	 * NOT IMPLEMENTED
	 */
	private boolean isPublic;

	/**
	 * One card can belong to many different decks and one deck can have many
	 * different cards.
	 * Duplicates not allowed
	 */
	@ManyToMany
	private Set<Card> cards;

	@ManyToMany
	private Set<Category> categories;

	public Deck() {
	}

	/**
	 * Create a deck builder instead?
	 * @param cards - may be null, empty deck will be created
	 */
	public Deck(String name, String creator, Set<Card> cards) {
		super();
		this.name = name;
		this.creator = creator;
		this.isPublic = false;
		if (cards == null) {
			cards = new HashSet<Card>();
		}
		this.cards = cards;
		autoComputeCategories();
	}

	private void autoComputeCategories() {
		this.categories = this.cards.stream().map(c -> c.getCategory()).collect(Collectors.toSet());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public void addCard(Card c) {
		if (this.cards.add(c)) {
			autoComputeCategories();
		}
	}

	public void removeCard(Card c) {
		if (this.cards.remove(c)) {
			autoComputeCategories();
		}
	}
	
	public Card draw() {
		List<Card> cardlist = this.cards.stream().collect(Collectors.toList());
		Card random = cardlist.remove(new Random().nextInt(cardlist.size()));
		this.cards = cardlist.stream().collect(Collectors.toSet());
		return random;
	}
	
	@Transient
	public List<Card> getShuffled(){
		List<Card> shuffled = new ArrayList<Card>(this.cards);
		Collections.shuffle(shuffled);
		return shuffled;
	}
}
