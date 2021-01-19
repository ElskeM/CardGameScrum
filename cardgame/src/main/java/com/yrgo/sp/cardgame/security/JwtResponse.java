package com.yrgo.sp.cardgame.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final JwtResponseUser user;

	public JwtResponse(String jwttoken, JwtResponseUser user) {
		this.jwttoken = jwttoken;
		this.user = user;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
