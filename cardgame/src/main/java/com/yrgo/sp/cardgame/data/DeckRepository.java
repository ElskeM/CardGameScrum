package com.yrgo.sp.cardgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.yrgo.sp.cardgame.domain.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long>{
	public List<Deck> findByName(@Param("name") String name);
}

