package com.yrgo.sp.cardgame.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.yrgo.sp.cardgame.chat.ChatMessage;
import com.yrgo.sp.cardgame.domain.Card;

@Controller
public class GameWSController implements GameIsDrawListener {

	@Autowired
	private GameService game;

	@Autowired
	private SimpMessagingTemplate template;

	// Webklienten prenumererar på dessa med id:t ifrån Gameinstansen
	@MessageMapping("/connected/{id}/{playerName}")
	@SendTo("/cardgame/connected/{id}")
	public boolean secondPlayerConnected(@DestinationVariable long id, @DestinationVariable String playerName) {
		System.out.println("secondPlayerConnected: " + true + ", gameID: " + id);
		Game g = game.getGameById(id);
		g.addPlayer(playerName);// .getPlayers().get(1).setName(playerName);

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
		Game g = game.getGameById(gameId);
		game.fillDeck(gameId);
		g.startNewGame();
		g.addGameIsDrawListener(this);
		g.getPlayers().get(ThreadLocalRandom.current().nextInt(0, g.getPlayers().size())).setTurn(true);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table", g.getTable());
		map.put("player", null);
		
		sendGameInfo(g);
		
		for (Player player : g.getPlayers()) {
			map.replace("player", player);
			this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
		}

	}

	private void sendGameInfo(Game g) {
		HashMap<String, Object> gameInfo = new HashMap<String, Object>();
		int matches = 0;
		HashMap<Integer, HashMap<String,String>> stats = new HashMap<Integer, HashMap<String,String>>();
		Integer tmp=0;
		for (Player player : g.getPlayers()) {
			stats.put(tmp++, new HashMap<>(Map.of("name", player.getName(),"wins",""+player.getWins())));
			matches += player.getWins();
		}
		gameInfo.put("players", stats);
		gameInfo.put("matches", matches);
		this.template.convertAndSend(("/cardgame/gameInfo/" + g.getId()), gameInfo);
		
	}

	@MessageMapping("/connected/playerMove/{id}/{playerName}")
	@SendTo("/cardgame/updateGameBoard/{id}")
	public List<MappedCard> cardPlayed(PlayerMove move, @DestinationVariable long id,
			@DestinationVariable String playerName) {
		System.out.println("PLAYER MOVE");
		System.out.println(move.getCardId());
		System.out.println(move.getPlayerName());
		System.out.println(move.getCardPosition());
		boolean correctMove = true;// Tanke att returnera om draget var rätt eller ej
		Game g = game.getGameById(id);

		Optional<Player> p = g.getPlayers().stream().filter(pl -> pl.getName().equals(playerName)).findFirst();
		Player currentPlayer = p.get();

		g.makeMove(currentPlayer, move.getCardId(), move.getCardPosition());

		g.changeTurnForPlayers(currentPlayer);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table", g.getTable());
		map.put("muck", g.getMuck());
		map.put("player", null);
		String winner = g.checkWin();
		map.put("winner", winner);
		if(winner!=null) {
			sendGameInfo(g);
		}
		for (Player player : g.getPlayers()) {
			map.replace("player", player);
			this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
		}
		return g.getTable();// Kanske inte behövs då alla spelare redan fått uppdaterat bord

	}

	@Override
	public void gameIsDraw(long id) {
		Game g = game.getGameById(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table", g.getTable());
		map.put("player", null);
		map.put("winner", "Oavgjort!");
		for (Player player : g.getPlayers()) {
			map.replace("player", player);
			this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
		}
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
