package com.yrgo.sp.cardgame.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.user.User;

/**
 * @author elske
 * This repository has been refactored, as it used to be the player repository
 * Repository interface for User
 * Extends JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Method to find user by username
	 * @param username
	 * @return found user
	 */
	Optional<User> findByUsername(String username);
	
	/**
	 * Method to find user by email
	 * @param email
	 * @return found user
	 */
	Optional<User> findByEmail(String email);
}
