package com.yrgo.sp.cardgame.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


/**
 * @author elske
 *Entity that saves a player and its favourite cards in the database
 */
@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)//TODO: nullable=false gör ingenting, varför?
	@NotEmpty
	private String userName;
	@Email
	private String email;
	@Column(nullable = false) //TODO: nullable=false gör ingenting, varför?
	@NotEmpty
	private String password; 
	
	@OneToMany//(mappedBy = "player")
	private Set<Card> favoriteCards;
	
	public Player() {}
	
	public Player(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.favoriteCards = new HashSet<Card>();
	}
	

	@Override
	public String toString() {
		return "Player [id=" + id + ", userName=" + userName + ", email=" + email + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void addCardsToFavorites(Card newCard) {
		this.favoriteCards.add(newCard);
	}

	public Set<Card> getFavoriteCards() {
		return this.favoriteCards;
	}

	
}
