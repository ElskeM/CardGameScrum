package com.yrgo.sp.cardgame.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.socket.WebSocketSession;

import com.yrgo.sp.cardgame.domain.Card;


@Controller
public class GameWSController {
	
	@Autowired
	private GameService game;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	//Webklienten prenumererar på dessa med id:t ifrån Gameinstansen
	@MessageMapping("/connected/{id}")
	@SendTo("/cardgame/connected/{id}")
	public boolean secondPlayerConnected(@DestinationVariable long id){
		System.out.println("secondPlayerConnected: " + true + ", gameID: " + id);
		game.createGame(id);
		placeInitialCard(id);
		return true;
	}
	
	/*Denna prenumererar båda spelarna på och får på så vis tillgång till spelets startkort
	till spelets startkort så snart spelare 2 anslutit sig till spelet */
	//@SendTo("/cardgame/startCard/{id}")
	public void placeInitialCard(long gameId) {
		System.out.println("DEALING FIRST CARD");
		Game g = game.getGameById(gameId);
		g.giveCardTo(null);
		this.template.convertAndSend("/cardgame/startCard/"+g.getId(), g.getTable());
	}
	
	
	
	/*Kallas så fort spelaren drar sitt kort. Om true har kommit två gånger via prenumerationen
	så har båda spelarna dragit ett kort och spelet kan starta */
	@MessageMapping("{id}/drawCard")
	@SendTo("/topic/drawn/{id}")
	public boolean drawCard(@DestinationVariable String id) {
		return true;
	}
	
	
	/*Kallas när spelaren lägger ut sitt kort */
	@MessageMapping("{id}/putCard")
	@SendTo("/topic/put/{id}")
	public Card putCard(@DestinationVariable String id, Card card){
		return card;
	}
	

}
