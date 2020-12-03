package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yrgo.sp.cardgame.domain.Card;

@RestController
@RequestMapping(value = "game")
@CrossOrigin(origins = "http://localhost:8081")
//@Scope("session")
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping
	public ResponseEntity<Game> startGame() {

		// skapar ett id för spelet - används inte nu men kan vara bra att ha för
		// multiplayer
//		String id = UUID.randomUUID().toString();
//		Game theGame = new Game(id);
//		session.setAttribute("game", theGame);
		// return "redirect:/game/start";
		Game game = gameService.createGame();
		this.games.add(game);
		game.setId((++lastId));

		return ResponseEntity.ok(game);
	}

	// @Value(value = "game")
	private List<Game> games = new ArrayList<Game>();
	private long lastId = -1;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Game> findGame(@PathVariable int id) {
		Optional<Game> g = this.games.stream().filter(a -> a.getId() == id).findFirst();
		if (!g.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(g.get());
	}


	@GetMapping(value = "/{id}/{playerName}/{guess}")
	public ResponseEntity<String> play(@PathVariable int id, @PathVariable String playerName, @PathVariable int guess) {

		Player player = new Player(playerName);
		Optional<Game> g = this.games.stream().filter(a -> a.getId() == id).findFirst();
		if (!g.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Game game = g.get();

		game.setPlayer(player);
		player.setGuess(guess);
		String message = game.whoWins();

		return ResponseEntity.ok(message);
	}


	@GetMapping(value = "/{id}/{playerName}/draw")
	public ResponseEntity<Card> draw(@PathVariable int id, @PathVariable String playerName, @PathVariable int guess) {

		Optional<Game> g = this.games.stream().filter(a -> a.getId() == id).findFirst();
		if (!g.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Game game = g.get();

		Optional<Player> p = game.getPlayers().stream().filter(a -> a.getName() == playerName).findFirst();
		Player player = p.get();

		player.setCard(game.getDeck().draw());

		return ResponseEntity.ok(player.getCard());
	}

	/*
	@GetMapping(value = "/{id}/{guess}")
	public ResponseEntity<String> play(@PathVariable int id, @PathVariable int guess) {
		Optional<Game> g = this.games.stream().filter(a -> a.getId() == id).findFirst();
		if (!g.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Game game = g.get();
		String message = game.whoWins(game.getNumber(), guess);
		this.games.remove(game);
		return ResponseEntity.ok(message);
	}
*/
}
