package com.yrgo.sp.cardgame.domain.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.yrgo.sp.cardgame.domain.Card;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private User user;
	
	@OneToMany//(mappedBy = "player")
	private Set<Card> favoriteCards;
	
	public Player(User user) {
		this.user = user;
		this.favoriteCards = new HashSet<Card>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setFavoriteCards(Set<Card> favoriteCards) {
		this.favoriteCards = favoriteCards;
	}

	public void addCardsToFavorites(Card newCard) {
		this.favoriteCards.add(newCard);
	}

	public Set<Card> getFavoriteCards() {
		return this.favoriteCards;
	}
}
