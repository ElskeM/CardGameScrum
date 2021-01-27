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

/**
 * @author elske
 * This domain class was created to make it possible for a player to save certain cards as "Favorite Cards" in the database.
 * The specific userstory was later declared 'Not viable' and this specific domain class has been replaced by the User class.
 * This class is to be set to deprecated in a later sprint
 */
@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@OneToOne
	private User user;
	
	
	
	/**
	 * Hashset to save Favorite cards to the DB
	 */
	@OneToMany//(mappedBy = "player")
	private Set<Card> favoriteCards;
	
	
	/**
	 * Empty constructor to create player object
	 */
	public Player() {
		
	}
	/**
	 * Constructor for the player class. After the user class was created, the constructor now takes a user object as a parameter.
	 * FavoriteCards is initialised here
	 * @param user
	 */
	public Player(User user) {
		this.user = user;
		this.favoriteCards = new HashSet<Card>();
	}

	
	/**
	 * Method to add a specific card to the hashset.
	 * @param newCard
	 */
	public void addCardsToFavorites(Card newCard) {
		this.favoriteCards.add(newCard);
	}
	
	// Getter and Setter methods
	
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


	public Set<Card> getFavoriteCards() {
		return this.favoriteCards;
	}
}
