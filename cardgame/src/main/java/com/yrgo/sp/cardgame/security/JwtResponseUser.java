package com.yrgo.sp.cardgame.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.yrgo.sp.cardgame.domain.user.User;

public class JwtResponseUser {

	private String username;
	private String email;
	private Collection<? extends GrantedAuthority> roles;
	
	public JwtResponseUser(User u) {
		this.username = u.getUsername();
		this.email = u.getEmail();
		this.roles = u.getAuthorities();
	}
	
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
