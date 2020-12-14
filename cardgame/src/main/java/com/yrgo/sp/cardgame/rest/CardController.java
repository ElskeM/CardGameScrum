package com.yrgo.sp.cardgame.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger LOG = LogManager.getLogger(CardController.class);
	
	@Autowired
	private CardRepository cardData;

	@Autowired
	private CategoryRepository categoryData;

	@GetMapping("/allCards")
	public ResponseEntity<CardList> allCards() {
		LOG.trace("Method findAllCards called");
		List<Card> allCards = cardData.findAll();
		
		if (allCards.isEmpty()) {
			LOG.trace("CardList is empty, returning no content status");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		LOG.trace("Adding found Cards to CardList object");
		CardList cards = new CardList(allCards);
		
		LOG.trace("Returning CardList with Cards to Client");
		return new ResponseEntity<>(cards, HttpStatus.OK);
	}

	@GetMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String name) throws IOException {
		LOG.trace("Method getImage called with following parameter: " + name);
		File f = new File("src/main/resources/images/klimatkoll/" + name);
		InputStream is = new FileInputStream(f);
		long fileSize = f.length();
		byte[] allBytes = new byte[(int) fileSize];
		is.read(allBytes);
		is.close();
		LOG.trace("Return image in bytes to Client");
		return allBytes;
	}

	@GetMapping("/card/{id}")
	public ResponseEntity<Card> findCard(@PathVariable long id) {
		LOG.trace("Method findCard called with following parameter: " + id);
		Optional<Card> foundCard = cardData.findById(id);
		
		if (foundCard.isEmpty()) {
			LOG.trace("Invalid parameter, casting CardNotFoundException");
			throw new CardNotFoundException();
		}
		LOG.trace("Card successfully found and returned to Client");
		return new ResponseEntity<>(foundCard.get(), HttpStatus.OK);
	}

	@PostMapping("/newCard")
	public ResponseEntity<Card> createNewCard(@RequestBody Card card) {
		LOG.trace("Method createNewCard is called with following parameter: " + card.toString());
		
		Card newCard = cardData.save(card);
		LOG.trace("Saving new Card in Repository");
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCard.getId())
				.toUri();
		LOG.trace("URI to new Card created and returned to Client");
		
		return ResponseEntity.created(location).build();
	}

	@PostMapping("/import_json")
	public String importJSONData(@RequestBody List<Card> cards) {
		LOG.trace("Method importJSONData called with following parameter: " + cards.toString());
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
			LOG.trace("CardData successfully saved in Repository");
		}
		return "Success!";
	}

	@PutMapping("/card/{id}")
	public ResponseEntity<Object> updateCard(@RequestBody Card card, @PathVariable Long id) {
		LOG.trace("Method updateCard called for Card with id: " + id);
		Optional<Card> c = cardData.findById(id);
		
		LOG.trace("Check if ID is valid and Card exists");
		if (c.isEmpty()) {
			LOG.trace("Invalid parameter, casting CardNotFoundException");
			throw new CardNotFoundException();
		}
		LOG.trace("Card successfully found, fetching CardEntity");
		Card cardToUpdate = c.get();
		
		LOG.trace("Updating Card with new data");
		card.setId(cardToUpdate.getId());
		
		LOG.trace("Saving updated Card in Repository");
		cardData.save(card);
		
		LOG.trace("Updated Card successfully saved in Repository and returned to Client");
		return new ResponseEntity<>(card, HttpStatus.OK);
	}

	@DeleteMapping("/card/{id}")
	public ResponseEntity<HttpStatus> deleteCard(@PathVariable Long id) {
		LOG.trace("Method deleteCard called for Card with id: " + id);
		cardData.deleteById(id);
		
		LOG.trace("Card successfully deleted, no content status returned to Client");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
