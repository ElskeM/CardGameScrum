package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.data.DeckRepository;
import com.yrgo.sp.cardgame.domain.Deck;
import com.yrgo.sp.exception.DeckNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class DeckController {

	private static final Logger LOG = LogManager.getLogger(DeckController.class);
	
	@Autowired
	private DeckRepository deckData;

	@Autowired
	private CardRepository cardData;

	@GetMapping("/decks")
	public ResponseEntity<List<Deck>> decks(@RequestParam(required = false) String name) {
		//TODO: implement searchByName
		
		LOG.info("Method decks called");
		List<Deck> allDecks = deckData.findAll();
	
		if (allDecks.isEmpty()) {
			LOG.info("DeckList is empty, returning no content status");			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		LOG.info("Returning List of Decks to Client");
		return new ResponseEntity<>(allDecks, HttpStatus.OK);
	}

	@GetMapping("/decks/{id}")
	public ResponseEntity<Object> findDeck(@PathVariable long id) {
		LOG.info("Method findDeck called with following parameter: " + id);
		Optional<Deck> foundDeck = deckData.findById(id);
		
		if (foundDeck.isEmpty()) {
			LOG.info("Invalid parameter, casting DeckNotFoundException");
			throw new DeckNotFoundException();
		}
		LOG.info("Deck successfully found and returned to Client");
		return new ResponseEntity<>(foundDeck.get(), HttpStatus.OK);
	}

	@PostMapping("/newDeck")
	public ResponseEntity<Object> createDeck(@RequestBody Deck deck) {
		LOG.info("Method createDeck called with following parameter: " + deck.toString());
		
		Deck newDeck = deckData.save(deck);
		LOG.info("Saving new Deck in Repository");
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDeck.getId())
				.toUri();
		LOG.info("URI to new Deck created and returned to Client");
		
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/decks/{id}")
	public ResponseEntity<Object> updateDeck(@RequestBody Deck deck, @PathVariable Long id) {
		LOG.info("Method updateDeck called for Deck with id: " + id);
		Optional<Deck> d = deckData.findById(id);
		
		LOG.info("Check if ID is valid and Deck exists");
		if (d.isEmpty()) {
			LOG.info("Invalid parameter, casting DeckNotFoundException");
			throw new DeckNotFoundException();
		}

		LOG.info("Deck successfully found, fetching DeckEntity");
		Deck oldDeck = d.get();
		
		LOG.info("Updating Deck with new data");
		deck.setId(oldDeck.getId());
		
		LOG.info("Saving updated Deck in Repository");
		deckData.save(deck);
		
		LOG.info("Updated Deck successfully saved in Repository and returned to Client");
		return new ResponseEntity<>(deck, HttpStatus.OK);
	}

	@DeleteMapping("/decks/{id}")
	public ResponseEntity<HttpStatus> deleteDeck(@PathVariable Long id) {
		LOG.info("Method deleteDeck called for Deck with id: " + id);
		deckData.deleteById(id);
		
		LOG.info("Deck successfully deleted, no content status returned to Client");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/decks/setUpData")
	public String setUpData() {

		Deck d = new Deck("Default", "Admin", cardData.findAll().stream().collect(Collectors.toSet()));
		deckData.save(d);
		return "Success!";
	}
}
