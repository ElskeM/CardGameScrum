package com.yrgo.sp.cardgame.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.Card;

public interface CardRepository extends JpaRepository<Card, Long>{
	public List<Card> findAll();

}
