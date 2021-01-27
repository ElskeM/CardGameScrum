package com.yrgo.sp.cardgame.game;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author ptemrz, pontus, simon
 *
 */
@Controller
public class GameWSController implements KlimatkollListener {

	@Autowired
	private GameService game;

	@Autowired
	private SimpMessagingTemplate template;

	// Webklienten prenumererar på dessa med id:t ifrån Gameinstansen
	@MessageMapping("/connected/{id}/{playerName}")
	@SendTo("/cardgame/connected/{id}")
	public boolean secondPlayerConnected(@DestinationVariable long id, @DestinationVariable String playerName) {
		System.out.println("secondPlayerConnected: " + true + ", gameID: " + id);
		Game g = game.getGameById(id).orElseThrow();
		g.addPlayer(playerName);

		System.out.println(g.getPlayers().get(1).getName());
		placeInitialCard(id);
		return true;
	}

	/*
	 * Denna prenumererar båda spelarna på och får på så vis tillgång till spelets
	 * startkort till spelets startkort så snart spelare 2 anslutit sig till spelet
	 */
	// @SendTo("/cardgame/startCard/{id}")
	public void placeInitialCard(long gameId) {
		System.out.println("DEALING FIRST CARD");
		Game g = game.getGameById(gameId).orElseThrow();
		game.fillDeck(gameId);
		g.startNewGame();
		g.addGameListener(this);
		g.getPlayers().get(ThreadLocalRandom.current().nextInt(0, g.getPlayers().size())).setTurn(true);
		HashMap<String, Object> map = createGameExportMap(g);
		
		sendGameInfo(g);
		
		sendPlayerData(g, map);

	}

	private void sendGameInfo(Game g) {
		this.template.convertAndSend(("/cardgame/gameInfo/" + g.getId()), new GameInfoDetails(g));
		
	}

	@MessageMapping("/connected/playerMove/{id}/{playerName}")
	@SendTo("/cardgame/madeMove/{id}/{playerName}")
	public boolean cardPlayed(PlayerMove move, @DestinationVariable long id,
			@DestinationVariable String playerName) {

		Game g = game.getGameById(id).orElseThrow();

		Optional<Player> p = g.getPlayers().stream().filter(pl -> pl.getName().equals(playerName)).findFirst();
		Player currentPlayer = p.get();


		boolean correctMove = g.makeMove(currentPlayer, move.getCardId(), move.getCardPosition());

		g.changeTurnForPlayers();

		sendGameUpdate(g);
		return correctMove;
	}

	@Override
	public void gameIsDraw(Game g) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table", g.getTable());
		map.put("muck", g.getMuck());
		map.put("player", null);
		map.put("winner", "Oavgjort!");
		sendPlayerData(g, map);
	}

	private void sendGameUpdate(Game g) {
		HashMap<String, Object> map = createGameExportMap(g);
		String winner = g.checkWin();
		map.put("winner", winner);
		if(winner!=null) {
			sendGameInfo(g);
		}
		sendPlayerData(g, map);
	}
	private void sendPlayerData(Game g, HashMap<String, Object> map) {
		for (Player player : g.getPlayers()) {
			map.replace("player", player);
			this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
		}

	}
	@Override
	public void timerRunOut(Game g) {
		sendGameUpdate(g);
	}

	@Override
	public void walkover(Game g, Player winner) {
		HashMap<String, Object> map = createGameExportMap(g);
		map.put("winner", winner.getName());
		sendGameInfo(g);
		sendPlayerData(g, map);
	}
	
	private HashMap<String, Object> createGameExportMap(Game g){
		HashMap<String, Object> map = new HashMap<String, Object>();// Innehåller informationen som skickas till klienten
		map.put("table", g.getTable());
		map.put("muck", g.getMuck());
		map.put("player", null);
		return map;
	}

	/*
	 * Kallas så fort spelaren drar sitt kort. Om true har kommit två gånger via
	 * prenumerationen så har båda spelarna dragit ett kort och spelet kan starta
	 */

	/*
	 * 
	 * @MessageMapping("{id}/{playerName}/drawCard")
	 * 
	 * @SendTo("/cardgame/drawn/{id}") public boolean drawCard(@DestinationVariable
	 * long id, @DestinationVariable String playerName) { Game g =
	 * game.getGameById(id); // g.giveCardTo(null);
	 * 
	 * return true; }
	 * 
	 */

	/*
	 * Kallas när spelaren lägger ut sitt kort
	 * 
	 * @MessageMapping("{id}/putCard")
	 * 
	 * @SendTo("/topic/put/{id}") public Card putCard(@DestinationVariable String
	 * id, Card card){ return card; }
	 * 
	 */

}
