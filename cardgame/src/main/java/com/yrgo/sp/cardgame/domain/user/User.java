package com.yrgo.sp.cardgame.domain.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author elske, ptemrz
 * Entity that saves a player and its favourite cards in the database
 */
@Entity
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@NotEmpty
	private String username;
	
	@Email
	@NotEmpty
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(nullable = false)
	@NotEmpty
	private String password;
	
	@ManyToMany(targetEntity = SecurityRole.class, fetch = FetchType.EAGER)
	private Set<? extends GrantedAuthority> roles;
	
	public User() {}
	
	public User(String userName, String email, String password, Collection<? extends GrantedAuthority> roles) {
		this.username = userName;
		this.email = email;
		this.password = password;
		this.roles = new HashSet<>(roles);
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + "]";
	}
	
	public void setRoles(Set<? extends GrantedAuthority> roles) {
		this.roles = roles;		
	}

	public void setUserName(String userName) {
		this.username = userName;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}	
}
