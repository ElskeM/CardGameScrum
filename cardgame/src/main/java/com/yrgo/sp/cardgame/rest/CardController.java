package com.yrgo.sp.cardgame.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.data.CategoryRepository;
import com.yrgo.sp.cardgame.domain.Card;
import com.yrgo.sp.cardgame.domain.Category;
import com.yrgo.sp.exception.CardNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:")
public class CardController {

	@Autowired
	private CardRepository cardData;

	@Autowired
	private CategoryRepository categoryData;

	@GetMapping("/allCards")
	public ResponseEntity<CardList> allCards() {
		List<Card> allCards = cardData.findAll();
		if (allCards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		CardList cards = new CardList(allCards);
		return new ResponseEntity<>(cards, HttpStatus.OK);
	}

	@GetMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String name) throws IOException {
		File f = new File("src/main/resources/images/klimatkoll/" + name);
		InputStream is = new FileInputStream(f);
		long fileSize = f.length();
		byte[] allBytes = new byte[(int) fileSize];
		is.read(allBytes);
		is.close();
		return allBytes;
	}

	@GetMapping("/card/{id}")
	public ResponseEntity<Card> findCard(@PathVariable long id) {
		Card foundCard = cardData.findById(id)
				.orElseThrow(() -> new CardNotFoundException("Kunde inte hitta kort med id " + id));
		return new ResponseEntity<>(foundCard, HttpStatus.OK);
	}

	@PostMapping("/newCard")
	public ResponseEntity<Card> createNewCard(@RequestBody Card card) {
		Card newCard = cardData.save(card);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCard.getId())
				.toUri();
		return ResponseEntity.created(location).build();
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
			c.setFrontImage("http://localhost:8080" + c.getFrontImage());
			c.setBackImage("http://localhost:8080" + c.getBackImage());
			cardData.save(c);
		}
		return "Success!";
	}

	@PutMapping("/card/{id}")
	public ResponseEntity<Object> updateCard(@RequestBody Card card, @PathVariable Long id) {
		Optional<Card> c = cardData.findById(id);
		if (!c.isPresent()) {
			throw new CardNotFoundException("Kunde inte hitta kort med id " + id);
		}

		Card cardToUpdate = c.get();
		card.setId(cardToUpdate.getId());
		cardData.save(card);
		return new ResponseEntity<>(card, HttpStatus.OK);
	}

	@DeleteMapping("/card/{id}")
	public ResponseEntity<HttpStatus> deleteCard(@PathVariable Long id) {
		cardData.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
