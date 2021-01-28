package com.yrgo.sp.cardgame.security;

import java.io.Serializable;

/**
 * @author ptemrz
 * JwtRequest Entity
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;

	
	/**
	 * Default constructor for JSON Parsing 
	 */
	public JwtRequest() {

	}

	/**
	 * Constructor for jwtrequest
	 * @param username
	 * @param password
	 */
	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	// Getter and Setter methods
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
