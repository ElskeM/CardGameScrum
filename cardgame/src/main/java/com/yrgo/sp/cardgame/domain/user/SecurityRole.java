package com.yrgo.sp.cardgame.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

@Entity(name = "ROLES")
public class SecurityRole implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3685135056373149542L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String authority;
	
	
	
	private Long getId() {
		return id;
	}



	private void setId(Long id) {
		this.id = id;
	}



	private void setAuthority(String authority) {
		this.authority = authority;
	}



	@Override
	public String getAuthority() {
		return authority;
	}

}
