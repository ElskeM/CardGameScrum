package com.yrgo.sp.cardgame.game;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "game")
@CrossOrigin(origins = "http://localhost:8081")
public class GameController {

	@RequestMapping(value = "start")
	public String startGame(HttpSession session) {

		// skapar ett id för spelet - används inte nu men kan vara bra att ha för
		// multiplayer
		String id = UUID.randomUUID().toString();
		Game theGame = new Game(id);
		session.setAttribute("game", theGame);
		// return "redirect:/game/start";
		return "Det gick bra";

	}

	@RequestMapping(value = "/start/{userNumber}")
	public void setUserNumber(HttpSession session, @PathVariable("userNumber") String userNumber) {
		Game theGame = (Game) session.getAttribute("game");
		int userNum = Integer.parseInt(userNumber);
		theGame.setUserNumber(userNum);

	}

	@RequestMapping(value = "/start/play")
	public String play(HttpSession session) {
		Game theGame = (Game) session.getAttribute("game");
		return theGame.whoWins(theGame.getNumber(), theGame.getUserNumber());
	}

}
