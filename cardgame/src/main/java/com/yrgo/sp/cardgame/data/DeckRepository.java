package com.yrgo.sp.cardgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Deck;

/**
 * @author 
 * Repository interface for Deck
 * Extends JpaRepository
 */
public interface DeckRepository extends JpaRepository<Deck, Long>{
	
	/**
	 * Method to find Deck by name
	 * @param name
	 * @return List of found Decks
	 */
	public List<Deck> findByName(String name);
}

