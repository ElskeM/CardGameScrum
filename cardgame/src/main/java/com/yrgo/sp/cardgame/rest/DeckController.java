package com.yrgo.sp.cardgame.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class DeckController {
	@Autowired
	private DeckRepository deckData;
	
	@Autowired
	private CardRepository cardData;
	
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping("/decks")
	public List<Deck> decks(@RequestParam(required = false) String name) {
		if (name != null) {
			return deckData.findByName(name);
		}
		return deckData.findAll();
	}
	
	@GetMapping("/decks/{id}")
	public Deck findDeck(@PathVariable long id) {
		return deckData.findById(id).get();
	}
	
	@PostMapping("/decks")
	public ResponseEntity<Object> createDeck(@RequestBody Deck deck) {
		Deck savedDeck = deckData.save(deck);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedDeck.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/decks/{id}")
	public ResponseEntity<Object> updateDeck(@RequestBody Deck deck, @PathVariable Long id) {
		Optional<Deck> d = deckData.findById(id);
		if (!d.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Deck oldDeck = d.get();
		deck.setId(oldDeck.getId());
		deckData.save(deck);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/decks/{id}")
	public void deleteDeck(@PathVariable Long id) {
		deckData.deleteById(id);
	}
	
	@GetMapping("/decks/setUpData")
	public String setUpData() {
		
		Deck d = new Deck("Default","Admin",cardData.findAll().stream().collect(Collectors.toSet()));
		deckData.save(d);
		return "Success!";
	}
}
