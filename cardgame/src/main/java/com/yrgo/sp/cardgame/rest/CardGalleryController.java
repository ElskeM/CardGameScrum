package com.yrgo.sp.cardgame.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yrgo.sp.cardgame.domain.Card;

@RestController
public class CardGalleryController {
	
	@GetMapping("/allCards")
	public List<Card> allCards() {
		List<Card> allCards = new ArrayList<Card>();
		return allCards;
	}
}
