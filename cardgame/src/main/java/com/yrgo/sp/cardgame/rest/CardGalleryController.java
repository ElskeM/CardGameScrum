package com.yrgo.sp.cardgame.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.domain.Card;


@RestController
public class CardGalleryController {
	

	@Autowired
	private CardRepository cardData;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/allCards")
	public CardList allCards() {
		List<Card> allCards = cardData.findAll();
		
		return new CardList(allCards);
	}
	
	@GetMapping("/setUpData")
	public String setUpData() {
		Card card1 = new Card("Klippa gräs", 1000);
		Card card2 = new Card("Äta korv", 1200);
		Card card3 = new Card("Cykla", 100);
		Card card4 = new Card("Flyga", 5000);
		
		cardData.save(card1);
		cardData.save(card2);
		cardData.save(card3);
		cardData.save(card4);
		
		return "Success!";
	}
}
