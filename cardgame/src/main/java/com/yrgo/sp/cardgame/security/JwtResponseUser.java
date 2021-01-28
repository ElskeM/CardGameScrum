package com.yrgo.sp.cardgame.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.yrgo.sp.cardgame.domain.user.User;

/**
 * @author ptemrz
 * JwtResponseUser class
 */
public class JwtResponseUser {

	private String username;
	private String email;
	private Collection<? extends GrantedAuthority> roles;
	
	/**
	 * Constructor
	 * @param user
	 */
	public JwtResponseUser(User u) {
		this.username = u.getUsername();
		this.email = u.getEmail();
		this.roles = u.getAuthorities();
	}
	
	// Getter methods
	
	public String getUsername() {
		return this.username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Collection<? extends GrantedAuthority> getRoles(){
		return roles;
	}
}
