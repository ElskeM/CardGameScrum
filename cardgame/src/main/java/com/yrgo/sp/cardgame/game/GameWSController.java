package com.yrgo.sp.cardgame.game;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.yrgo.sp.cardgame.domain.Card;

@Controller
public class GameWSController {

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
		g.addPlayer(playerName);//.getPlayers().get(1).setName(playerName);

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

		g.getPlayers().get(ThreadLocalRandom.current().nextInt(0, g.getPlayers().size())).setTurn(true);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table", g.getTable());
		map.put("player", null);
		for (Player player : g.getPlayers()) {
			map.replace("player", player);
			this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
		}
	}

	@MessageMapping("/connected/playerMove/{id}/{playerName}")
	@SendTo("/cardgame/updateGameBoard/{id}")
	public List<Card> cardPlayed(PlayerMove move, @DestinationVariable long id,
			@DestinationVariable String playerName) {
		System.out.println("PLAYER MOVE");
		System.out.println(move.getCardId());
		System.out.println(move.getPlayerName());
		System.out.println(move.getCardPosition());
		boolean correctMove=true;//Tanke att returnera om draget var rätt eller ej
		Game g = game.getGameById(id);

		//List<Card> table = g.getTable();

		Optional<Player> p = g.getPlayers().stream().filter(pl -> pl.getName().equals(playerName)).findFirst();
		Player currentPlayer = p.get();

		if (!currentPlayer.isTurn()) {// Exception av slag här va? Det var inte den här spelarens tur!
			//return null;
		}

		

		g.makeMove(currentPlayer, move.getCardId(), move.getCardPosition());
	
		g.changeTurnForPlayers(currentPlayer);
//		if (g.getPlayers().size() > (g.getPlayers().indexOf(currentPlayer) + 1)) {
//			g.getPlayers().get(g.getPlayers().indexOf(currentPlayer) + 1).setTurn(true);
//			
//		} else {
//			g.getPlayers().get(0).setTurn(true);
//			
//		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("table", g.getTable());
		map.put("player", null);
		for (Player player : g.getPlayers()) {
			map.replace("player", player);
			this.template.convertAndSend(("/cardgame/startCard/" + g.getId() + "/" + player.getName()), map);
		}
		return g.getTable();//Kanske inte behövs då alla spelare redan fått uppdaterat bord

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
