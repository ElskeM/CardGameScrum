package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class DeckController {

	@Autowired
	private DeckRepository deckData;

	@Autowired
	private CardRepository cardData;

	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/decks")
	public ResponseEntity<List<Deck>> decks(@RequestParam(required = false) String name) {
		List<Deck> allDecks = deckData.findAll();
		if (allDecks.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(allDecks, HttpStatus.OK);
	}

	@GetMapping("/decks/{id}")
	public ResponseEntity<Object> findDeck(@PathVariable long id) {
		Optional<Deck> foundDeck = deckData.findById(id);
		if (!foundDeck.isPresent()) {
			throw new DeckNotFoundException("Kunde inte hitta deck med id: " + id);
		}
		return new ResponseEntity<>(foundDeck, HttpStatus.OK);
	}

	@PostMapping("/newDeck")
	public ResponseEntity<Object> createDeck(@RequestBody Deck deck) {
		Deck newDeck = deckData.save(deck);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDeck.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/decks/{id}")
	public ResponseEntity<Object> updateDeck(@RequestBody Deck deck, @PathVariable Long id) {
		Optional<Deck> d = deckData.findById(id);
		if (!d.isPresent()) {
			throw new DeckNotFoundException("Kunde inte hitta deck med id " + id);
		}

		Deck oldDeck = d.get();
		deck.setId(oldDeck.getId());
		deckData.save(deck);
		return new ResponseEntity<>(deck, HttpStatus.OK);
	}

	@DeleteMapping("/decks/{id}")
	public ResponseEntity<HttpStatus> deleteDeck(@PathVariable Long id) {
		deckData.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/decks/setUpData")
	public String setUpData() {

		Deck d = new Deck("Default", "Admin", cardData.findAll().stream().collect(Collectors.toSet()));
		deckData.save(d);
		return "Success!";
	}
}
