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
 * GameWSController class which implements KlimatkollListener interface
 */
@Controller
public class GameWSController implements KlimatkollListener {

	@Autowired
	private GameService game;

	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * Webklienten prenumererar på dessa med id:t ifrån Gameinstansen
	 * @param id
	 * @param playerName
	 * @return
	 */
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
	

	/**
	 * Denna prenumererar båda spelarna på och får på så vis tillgång till spelets
	 * startkort till spelets startkort så snart spelare 2 anslutit sig till spelet
	 * @param gameId
	 */
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

	/**
	 * Method to send the gameinfo (details) to the client
	 * @param game
	 */
	private void sendGameInfo(Game g) {
		this.template.convertAndSend(("/cardgame/gameInfo/" + g.getId()), new GameInfoDetails(g));
		
	}

	/**
	 * Method that registers the played card by a player, 
	 * sets the turn to the other player and updates the game accordingly.
	 * @param move
	 * @param id
	 * @param playerName
	 * @return
	 */
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

	/**
	 * method that keeps track if a game is draw 
	 * (both players have played their last card in the same round)
	 * @param game
	 */
	@Override
	public void gameIsDraw(Game g) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table", g.getTable());
		map.put("muck", g.getMuck());
		map.put("player", null);
		map.put("winner", "Oavgjort!");
		sendPlayerData(g, map);
	}

	/** Method to send game updates to the client.
	 * @param game
	 */
	private void sendGameUpdate(Game g) {
		HashMap<String, Object> map = createGameExportMap(g);
		String winner = g.checkWin();
		map.put("winner", winner);
		if(winner!=null) {
			sendGameInfo(g);
		}
		sendPlayerData(g, map);
	}
	
	/** 
	 * Method to send playerdata to the client 
	 * @param game
	 * @param map
	 */
	private void sendPlayerData(Game g, HashMap<String, Object> map) {
		for (Player player : g.getPlayers()) {
			map.replace("player", player);
			this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
		}

	}
	/**
	 * method sends an update to the client when the timer runs out
	 * @param game
	 */
	@Override
	public void timerRunOut(Game g) {
		sendGameUpdate(g);
	}

	/**
	 * Method sends updated info after a player has missed their turn three times in a row
	 * The other player automatically wins then.
	 *@param game, player
	 */
	@Override
	public void walkover(Game g, Player winner) {
		HashMap<String, Object> map = createGameExportMap(g);
		map.put("winner", winner.getName());
		sendGameInfo(g);
		sendPlayerData(g, map);
	}
	
	/**
	 * method returns a hashmap with table, muck and player info
	 * @param game
	 * @return hashmap
	 */
	private HashMap<String, Object> createGameExportMap(Game g){
		HashMap<String, Object> map = new HashMap<String, Object>();// Innehåller informationen som skickas till klienten
		map.put("table", g.getTable());
		map.put("muck", g.getMuck());
		map.put("player", null);
		return map;
	}

}
