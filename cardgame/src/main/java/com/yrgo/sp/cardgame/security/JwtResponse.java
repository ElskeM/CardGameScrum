package com.yrgo.sp.cardgame.security;

import java.io.Serializable;

/**
 * @author ptemrz
 * JwtResponse entity
 */
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final JwtResponseUser user;

	/**
	 * Constructor
	 * @param jwttoken
	 * @param user
	 */
	public JwtResponse(String jwttoken, JwtResponseUser user) {
		this.jwttoken = jwttoken;
		this.user = user;
	}

	// Getter methods
	
	public String getToken() {
		return this.jwttoken;
	}
	
	public JwtResponseUser getUser() {
		return this.user;
	}
}
