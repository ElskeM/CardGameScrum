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
		Card card1 = new Card("Klippa gräs", 1000);
		card1.setId(1);
		Card card2 = new Card("Klippa gräs", 1000);
		card2.setId(2);
		Card card3 = new Card("Klippa gräs", 1000);
		card3.setId(3);
		Card card4 = new Card("Klippa gräs", 1000);
		card4.setId(4);
		
		List<Card> allCards = new ArrayList<Card>();
		allCards.add(card1);
		allCards.add(card2);
		allCards.add(card3);
		allCards.add(card4);
		return allCards;
	}
}
