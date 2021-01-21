package com.yrgo.sp.cardgame.game;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yrgo.sp.cardgame.security.annotations.IsPlayer;

@RestController
@RequestMapping(value = "game")
@CrossOrigin(origins = "http://localhost:8081")
//@Scope("session")
public class GameController {

	@Autowired
	private GameService gameService;

	@Autowired
	private SimpMessagingTemplate template;

	private long lastId = 0;

	@IsPlayer
	@GetMapping(value = "/{firstPlayerName}")
	public ResponseEntity<Game> startGame(@PathVariable String firstPlayerName) {
		Game game = gameService.createGame(++lastId);
		game.addPlayer(firstPlayerName);// .getPlayers().get(0).setName(firstPlayerName);
		System.out.println("Storlek på listan spelare: " + game.getPlayers().size());
		System.out.println(game.getPlayers().get(0).getName());

		return ResponseEntity.ok(game);
	}

	@GetMapping(value = "/{id}/confirm")
	public ResponseEntity<String> restartGame(@PathVariable Long id) {

		Game g = gameService.getGameById(id);
		if (g.confirmReplay() == true) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("table", g.getTable());
			map.put("player", null);
			map.put("muck", g.getMuck());
			for (Player player : g.getPlayers()) {
				map.replace("player", player);
				this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
			}
		}
		return new ResponseEntity<>("Check", HttpStatus.OK);

	}
}