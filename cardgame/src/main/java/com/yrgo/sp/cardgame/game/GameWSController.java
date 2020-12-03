package com.yrgo.sp.cardgame.game;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.yrgo.sp.cardgame.domain.Card;


@Controller
public class GameWSController {
	
	//Webklienten prenumererar på dessa med id:t ifrån Gameinstansen
	
	
	@MessageMapping("/connected/{id}")
	@SendTo("/cardgame/connected/{id}")
	public boolean secondPlayerConnected(@DestinationVariable String id){
		System.out.println("secondPlayerConnected: " + true + ", gameID: " + id);
		return true;
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
