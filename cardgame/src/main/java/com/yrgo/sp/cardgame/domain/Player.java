package com.yrgo.sp.cardgame.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 * @author elske
 *Entity that saves a player and its favourite cards in the database
 */
@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(unique=true, nullable=false)
	private String userName;
	private String email;
	private String password;
	
	@OneToMany(mappedBy = "player")
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

	public int getId() {
		return id;
	}

	public void addStudentToTeachingGroup(Card newCard) {
		this.favoriteCards.add(newCard);
	}

	public Set<Card> getFavoriteCards() {
		return this.favoriteCards;
	}

	
}
