package com.yrgo.sp.cardgame.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.user.SecurityRole;

/**
 * @author 
 * Repository interface for Security roles
 * Extends JpaRepository
 */
public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long>{
	
	/**
	 * Method to find securityRole by authority name
	 * @param authority
	 * @return found SecurityRole
	 */
	Optional<SecurityRole> findByAuthority(String authority);
}
