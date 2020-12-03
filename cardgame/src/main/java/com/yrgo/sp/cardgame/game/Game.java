package com.yrgo.sp.cardgame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.domain.Card;
import com.yrgo.sp.cardgame.domain.Deck;

public class Game {

	private Deck deck;

	private List<Player> players;
	private long id;
	private ArrayList<Card> playedCards;


	public Game() {
		this.players = new ArrayList<Player>();
		this.playedCards = new ArrayList<Card>();
	}
	
	public Card drawCard() {
		return this.deck.draw();
	}
	
	public void setPlayer(Player player) {
		players.add(player);

	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public Deck getDeck() {
		return deck;
	}

	public ArrayList<Card> playCard(Card playedCard) {
		playedCards.add(playedCard);
		Collections.sort(playedCards);
		return playedCards;
	}
	
	
	
	public String startGame() {

		while (players.size() != 2) {
			try {
				Thread.sleep(500);
				System.out.println("HEEEEEEEJ");
			} catch (InterruptedException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return "ok";
	}

	
	
	
	/*
	 * public void setPlayer(Player player) { if players.size() == 2) {
	 * players.add(player) whoWins() } else { players.add(player) }
	 *
	 * }
	 *
	 */
	
	
	
/*	
	public String whoWins() {

		while (players.size() != 2) {
			try {
				Thread.sleep(500);
				System.out.println("HEEEEEEEJ");
			} catch (InterruptedException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(players.get(0).getGuess());
		System.out.println(players.get(1).getGuess());
		if (players.get(0).getGuess() > players.get(1).getGuess()) {

			return players.get(0).getName();
		} else if (players.get(0).getGuess() < players.get(1).getGuess()) {
			return players.get(1).getName();
		} else {
			return "Oavgjort";
		}

	}
*/
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	/*
	 * loopa listan jämför players(0).guess med players(1).guess kolla vem som van
	 * return vinnandePlayer.name + "vann";
	 */

	/*
	 *
	 * private int number = new Random().nextInt(10); private int userNumber;
	 *
	 * public Game() { // this.id = UUID.randomUUID().toString(); // this.id = id; }
	 *
	 * public String whoWins(int number, int userNumber) { if(number > userNumber) {
	 * return "YOU LOSE!"; } else { return "YOU WIN!"; } }
	 *
	 * public void setId(long id) { this.id = id; }
	 *
	 * public long getId() { return this.id; }
	 *
	 * public void setUserNumber(int userNumber) { this.userNumber = userNumber; }
	 *
	 * public int getNumber() { return number; }
	 *
	 * public int getUserNumber() { return userNumber; }
	 *
	 */
}
