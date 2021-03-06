package com.yrgo.sp.cardgame.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author ptemrz
 * Securityrole entity
 */
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

	
	/**
	 * Empty constructor 
	 */
	public SecurityRole() {

	}

	/**
	 * Constructor for SecurityRole
	 * @param authority
	 */
	public SecurityRole(String authority) {
		this.authority = authority;
	}

	// Getter and Setter methods
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
