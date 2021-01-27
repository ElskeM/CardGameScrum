package com.yrgo.sp.cardgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Card;

/**
 * @author ptemrz
 * Repository interface for Cards
 * Extends JpaRepository
 */
public interface CardRepository extends JpaRepository<Card, Long>{
	
	
	/**
	 * Method to find all Cards
	 * @return List of Cards 
	 */
	public List<Card> findAll();

}
