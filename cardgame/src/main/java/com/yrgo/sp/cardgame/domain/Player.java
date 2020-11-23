package com.yrgo.sp.cardgame.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String userName;
	private String email;
	private String password;
	
	public Player() {}
	
	public Player(String userName, String email, String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
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

	
}
