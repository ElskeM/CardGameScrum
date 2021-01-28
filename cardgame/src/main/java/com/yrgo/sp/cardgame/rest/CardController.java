package com.yrgo.sp.cardgame.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yrgo.sp.cardgame.exception.CardNotFoundException;
import com.yrgo.sp.cardgame.security.annotations.IsCreator;

/**
 * @author pontus, simon
 * CardController class which takes care of the cross origin and mapping for the client project
 */
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class CardController {

	private static final Logger LOG = LoggerFactory.getLogger(CardController.class);
	
	@Autowired
	private CardRepository cardData;

	@Autowired
	private CategoryRepository categoryData;

	/**
	 * Method that calls upon the findall method in the cardRepository
	 * If the list is empty an no content httpstatus is returned
	 * @return ResponseEntity with Cardlist with all cards
	 */
	@GetMapping("/allCards")
	public ResponseEntity<CardList> allCards() {
		LOG.info("Method findAllCards called");
		List<Card> allCards = cardData.findAll();
		
		if (allCards.isEmpty()) {
			LOG.info("CardList is empty, returning no content status");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		LOG.info("Adding found Cards to CardList object");
		CardList cards = new CardList(allCards);
		
		LOG.info("Returning CardList with Cards to Client");
		return new ResponseEntity<>(cards, HttpStatus.OK);
	}

	/**
	 * Method that returns card images as an array of bytes to the client
	 * @param String name
	 * @return Array of bytes
	 * @throws IOException
	 */
	@GetMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String name) throws IOException {
		LOG.info("Method getImage called with following parameter: " + name);
		File f = new File("src/main/resources/images/klimatkoll/" + name);
		InputStream is = new FileInputStream(f);
		long fileSize = f.length();
		byte[] allBytes = new byte[(int) fileSize];
		is.read(allBytes);
		is.close();
		LOG.info("Return image in bytes to Client");
		return allBytes;
	}

	/**
	 * Method to find a card with a specific id
	 * If the card doesn't exist, a cardnotfoundexception will be casted
	 * @param id
	 * @return ResponseEntity and found card
	 */
	@GetMapping("/card/{id}")
	public ResponseEntity<Card> findCard(@PathVariable long id) {
		LOG.info("Method findCard called with following parameter: " + id);
		Optional<Card> foundCard = cardData.findById(id);
		
		if (foundCard.isEmpty()) {
			LOG.info("Invalid parameter, casting CardNotFoundException");
			throw new CardNotFoundException();
		}
		LOG.info("Card successfully found and returned to Client");
		return new ResponseEntity<>(foundCard.get(), HttpStatus.OK);
	}

	/**
	 * Method to create a new card in the db, can only be called when one has a creator role
	 * @param card
	 * @return ResponseEntity and the uri to new card
	 */
	@IsCreator
	@PostMapping("/newCard")
	public ResponseEntity<Card> createNewCard(@RequestBody Card card) {
		LOG.info("Method createNewCard is called with following parameter: " + card.toString());
		
		Card newCard = cardData.save(card);
		LOG.info("Saving new Card in Repository");
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCard.getId())
				.toUri();
		LOG.info("URI to new Card created and returned to Client");
		
		return ResponseEntity.created(location).build();
	}

	/**
	 * Method to import the cards in the form of json data into the db 
	 * This method can be used to fill the database with cards
	 * @param cards
	 * @return String
	 */
	@PostMapping("/import_json")
	public String importJSONData(@RequestBody List<Card> cards) {
		LOG.info("Method importJSONData called with following parameter: " + cards.toString());
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
			LOG.info("CardData successfully saved in Repository");
		}
		return "Success!";
	}

	/**
	 * Method to update a card in the db. It can only be called when one has a creator role.
	 * @param card
	 * @param id
	 * @return ResponseEntity and the updated card
	 */
	@IsCreator
	@PutMapping("/card/{id}")
	public ResponseEntity<Object> updateCard(@RequestBody Card card, @PathVariable Long id) {
		LOG.info("Method updateCard called for Card with id: " + id);
		Optional<Card> c = cardData.findById(id);
		
		LOG.info("Check if ID is valid and Card exists");
		if (c.isEmpty()) {
			LOG.info("Invalid parameter, casting CardNotFoundException");
			throw new CardNotFoundException();
		}
		LOG.info("Card successfully found, fetching CardEntity");
		Card cardToUpdate = c.get();
		
		LOG.info("Updating Card with new data");
		card.setId(cardToUpdate.getId());
		
		LOG.info("Saving updated Card in Repository");
		cardData.save(card);
		
		LOG.info("Updated Card successfully saved in Repository and returned to Client");
		return new ResponseEntity<>(card, HttpStatus.OK);
	}

	/**
	 * Method to delete a card from the db. This can only be done when one has a creator role.
	 * @param id
	 * @return ResponseEntity
	 */
	@IsCreator
	@DeleteMapping("/card/{id}")
	public ResponseEntity<HttpStatus> deleteCard(@PathVariable Long id) {
		LOG.info("Method deleteCard called for Card with id: " + id);
		cardData.deleteById(id);
		
		LOG.info("Card successfully deleted, no content status returned to Client");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
