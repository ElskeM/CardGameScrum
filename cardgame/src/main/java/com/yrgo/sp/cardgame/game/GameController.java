package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
	// @Value(value = "game")
	private long lastId = 0;

	@GetMapping(value = "/{firstPlayerName}")
	public ResponseEntity<Game> startGame(@PathVariable String firstPlayerName) {
		Game game = gameService.createGame(++lastId);
		game.addPlayer(firstPlayerName);//.getPlayers().get(0).setName(firstPlayerName);
		System.out.println("Storlek på listan spelare: "+game.getPlayers().size());
		System.out.println(game.getPlayers().get(0).getName());
		
		return ResponseEntity.ok(game);
	}
	
	

/*	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Game> findGame(@PathVariable long id) {
		Game g = gameService.getGameById(id);
		if (g == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(g);
	}
	
	@GetMapping(value = "/{id}/{playerName}")
	public ResponseEntity<String> startGame(@PathVariable int id, @PathVariable String playerName) {
		
		Game g = gameService.getGameById(id);
		if (g == null) {
			return ResponseEntity.notFound().build();
		}
		
		Player player = new Player(playerName);
		g.setPlayer(player);
		String message = g.startGame();
		
		return ResponseEntity.ok(message);
	}
	
*/

	/*
	@GetMapping(value = "/{id}/{playerName}/draw")
	public ResponseEntity<Card> draw(@PathVariable int id, @PathVariable String playerName) {
		
		Game game = gameService.getGameById(id);
		if (game == null) {
			return ResponseEntity.notFound().build();
		}
		
		Optional<Player> p = game.getPlayers().stream().filter(a -> a.getName() == playerName).findFirst();
		Player player = p.get();
		
		player.setCard(game.getDeck().draw());
		
		return ResponseEntity.ok(player.getCard());
	}
	
*/	
	
/*	
	@Autowired
	private SimpMessagingTemplate template;


	@RequestMapping(value = "/sendMessage")
	public void sendMessage() throws Exception {
	    this.template.convertAndSend("/topic/greetings", new HelloMessage(
	            (int) Math.random(), "This is Send From Server"));
	}
	
*/	
	
	
	
//	@GetMapping(value = "/{id}/{playerName}/{guess}")
//	public ResponseEntity<String> play(@PathVariable int id, @PathVariable String playerName, @PathVariable int guess) {
//		
//		Player player = new Player(playerName);
//		Optional<Game> g = this.games.stream().filter(a -> a.getId() == id).findFirst();
//		if (!g.isPresent()) {
//			return ResponseEntity.notFound().build();
//		}
//		Game game = g.get();
//		
//		game.setPlayer(player);
//		player.setGuess(guess);
//		String message = game.whoWins();
//		
//		return ResponseEntity.ok(message);
//	}
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