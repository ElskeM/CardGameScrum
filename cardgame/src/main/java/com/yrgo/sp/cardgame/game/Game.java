package com.yrgo.sp.cardgame.game;

import java.io.Serializable;
import java.util.UUID;

<<<<<<< HEAD
public class Game{
	
=======
public class Game {

>>>>>>> 92df270b9cc8da1d9f39014b0ab26d91d555deac
	private String id;
	private int number = 10;
	private int userNumber;

	public Game(String id) {
		// this.id = UUID.randomUUID().toString();
		this.id = id;
	}

	public String whoWins(int number, int userNumber) {
		if (number > userNumber) {
			return "YOU LOSE!";
		} else {
			return "YOU WIN!";
		}
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public int getNumber() {
		return number;
	}

	public int getUserNumber() {
		return userNumber;
	}

}
