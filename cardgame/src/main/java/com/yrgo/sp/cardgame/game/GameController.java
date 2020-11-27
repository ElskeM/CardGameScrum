package com.yrgo.sp.cardgame.game;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "game")
@CrossOrigin(origins = "http://localhost:8081")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@PostMapping
	public ResponseEntity<Game> startGame() {
		
		//skapar ett id för spelet - används inte nu men kan vara bra att ha för multiplayer
//		String id = UUID.randomUUID().toString();
//		Game theGame = new Game(id);
//		session.setAttribute("game", theGame);
	//	return "redirect:/game/start";
		Game game = gameService.createGame();
		this.game = game;
		return ResponseEntity.ok(game);
	}
	
	@Value(value = "game")
	private Game game;
	
	@GetMapping(value = "/{guess}")
	public String play(@PathVariable int guess) {
		return game.whoWins(game.getNumber(), guess);
	}

}
