package com.yrgo.sp.cardgame.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.data.CategoryRepository;
import com.yrgo.sp.cardgame.domain.Card;
import com.yrgo.sp.cardgame.domain.Category;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class CardGalleryController {

	@Autowired
	private CardRepository cardData;
	
	@Autowired
	private CategoryRepository categoryData;

	@GetMapping("/allCards")
	public CardList allCards() {
		List<Card> allCards = cardData.findAll();

		return new CardList(allCards);
	}
	
	@GetMapping(value = "/images/{name}", produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String name) throws IOException{
		File f = new File("src/main/resources/images/klimatkoll/"+name);
		InputStream is = new FileInputStream(f);
		long fileSize = f.length();
		byte[] allBytes = new byte[(int) fileSize];
		is.read(allBytes);
		is.close();
		return allBytes;
	}
	
	@PostMapping("/newCard")
	public ResponseEntity<Card> createNewCard(@RequestBody Card card) {
		cardData.save(card);
		return new ResponseEntity<Card>(card, HttpStatus.CREATED);
	}

	@PostMapping("/import_json")
	public String importJSONData(@RequestBody List<Card> cards) {
		for (Card c : cards) {
			Category cat = categoryData.findByCategory(c.getCategory().getCategory());
			if (cat == null) {
				cat = c.getCategory();
				categoryData.save(cat);
			}
			c.setCategory(cat);
			cardData.save(c);
		}
		return "Success!";
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
