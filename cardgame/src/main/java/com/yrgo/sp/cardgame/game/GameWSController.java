package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;

import com.yrgo.sp.cardgame.domain.Card;


@Controller
public class GameWSController {
	
	@Autowired
	private GameService game;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	//Webklienten prenumererar på dessa med id:t ifrån Gameinstansen
	@MessageMapping("/connected/{id}/{playerName}")
	@SendTo("/cardgame/connected/{id}")
	public boolean secondPlayerConnected(@DestinationVariable long id, @DestinationVariable String playerName){
		System.out.println("secondPlayerConnected: " + true + ", gameID: " + id);
		Game g = game.getGameById(id);
		g.getPlayers().get(1).setName(playerName);
		System.out.println(g.getPlayers().get(1).getName());
		placeInitialCard(id);
		return true;
	}
	
	/*Denna prenumererar båda spelarna på och får på så vis tillgång till spelets startkort
	till spelets startkort så snart spelare 2 anslutit sig till spelet */
	//@SendTo("/cardgame/startCard/{id}")
	public void placeInitialCard(long gameId) {
		System.out.println("DEALING FIRST CARD");
		Game g = game.getGameById(gameId);
		g.startNewGame();
		
		Player playerOne = g.getPlayers().get(0);
		Player playerTwo = g.getPlayers().get(1);
		
		List<ArrayList<Card>> cardList = new ArrayList<ArrayList<Card>>();
		cardList.add((ArrayList<Card>) g.getTable());
		cardList.add((ArrayList<Card>) playerOne.getHand());
		
		List<ArrayList<Card>> cardList2 = new ArrayList<ArrayList<Card>>();
		cardList2.add((ArrayList<Card>) g.getTable());
		cardList2.add((ArrayList<Card>) playerTwo.getHand());
		
		System.out.println(playerOne.getName());
		System.out.println(playerTwo.getName());
		this.template.convertAndSend(("/cardgame/startCard/"+g.getId() + "/" + playerOne.getName()), cardList );
		this.template.convertAndSend(("/cardgame/startCard/"+g.getId() + "/" + playerTwo.getName()), cardList2 );
		
		this.template.convertAndSend("/cardgame/startCard/"+g.getId(), g.getTable());
	}
	
	
	
	
	/*Kallas så fort spelaren drar sitt kort. Om true har kommit två gånger via prenumerationen
	så har båda spelarna dragit ett kort och spelet kan starta */

	
	
	@MessageMapping("{id}/{playerName}/drawCard")
	@SendTo("/cardgame/drawn/{id}")
	public boolean drawCard(@DestinationVariable long id, @DestinationVariable String playerName) {
		Game g = game.getGameById(id);
//		g.giveCardTo(null);
		
		return true;
	}
	
	
	/*Kallas när spelaren lägger ut sitt kort */
	@MessageMapping("{id}/putCard")
	@SendTo("/topic/put/{id}")
	public Card putCard(@DestinationVariable String id, Card card){
		return card;
	}
	

}
