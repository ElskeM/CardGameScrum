package com.yrgo.sp.cardgame.game;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;



public class Game {

	@JsonIgnore
	private Deck deck;

	int turns;
	private List<Player> players;
	private long id;
	private List<MappedCard> table;
	private LinkedList<MappedCard> muck;//Slängda kort aka slasken
	private List<GameIsDrawListener> drawListeners = new ArrayList<GameIsDrawListener>();
	private int counter = 0;

	public Game(long id, int numberOfPlayers) {
		this.players = new ArrayList<Player>();// Skapa arraylist med storleken satt till antal spelare FUNKAR EJ!
		for (int i = 0; i < numberOfPlayers; i++) {
			players.add(null);
		}
		this.table = new ArrayList<MappedCard>();
		this.muck = new LinkedList<MappedCard>();
		// skapar en deck som fylls med kort i Decks konstruktor
		this.deck = new Deck();
		this.id = id;
	}
	
	public void startNewGame() {
		this.table.clear();
		this.muck.clear();
		turns = 0;
		this.table.add(this.deck.draw());
		this.table.add(this.deck.draw());
		Collections.sort(table);
		for (Player p : players) {
			p.getHand().clear();
			for (int i = 0; i < 3; i++) {
				p.addCardToHand(deck.draw());
			}

		}
	}
	
	public void addPlayer(String name) {
		try {
			players.set(players.indexOf(null), new Player(name));
		} catch (IndexOutOfBoundsException e) {
			// Players är full
		}
	}

	public List<MappedCard> getTable() {
		return this.table;
	}

	public List<MappedCard> getMuck() {
		return muck;
	}

	public MappedCard findCardInTableById(long id) {
		Optional<MappedCard> c = table.stream().filter(ca -> ca.getId() == id).findFirst();
		return c.get();
	}

	public boolean makeMove(Player player, long cardId, int index) {
		if (!player.isTurn()) {// Exception av slag här va? Det var inte den här spelarens tur!
			// return null;
		}
		Optional<MappedCard> pc = player.getHand().stream().filter(card -> card.getId() == cardId).findFirst();
		MappedCard playedCard = pc.get();
		player.getHand().remove(playedCard);
		table.add(index, playedCard);
		List<MappedCard> temp = new ArrayList<MappedCard>(table);
		Collections.sort(table);
		if (!(table.equals(temp))) {
			table.remove(playedCard);
			muck.push(playedCard);
			try {

				player.addCardToHand(deck.draw());
			} catch (IllegalArgumentException e) {
				// Deck is empty
				// DRAW
				for (GameIsDrawListener listener : drawListeners) {
					listener.gameIsDraw(id);
				}
			}
		}
		return true;
	}

	public String checkWin() {
		turns++;
		if (turns % players.size() == 0) {

			ArrayList<Player> winners = new ArrayList<>();
			for (Player player : players) {
				if (player.getHand().size() == 0) {
					winners.add(player);
				}
			}
			if (winners.size() > 1) {
				for (Player player : winners) {
					try {
						player.addCardToHand(deck.draw());
					} catch (IllegalArgumentException e) {
						
						for (GameIsDrawListener listener : drawListeners) {
							listener.gameIsDraw(id);
						}
						return "Oavgjort";
					}
				}
				return null;
			} else if (!winners.isEmpty()) {

				return winners.get(0).getName();
			}
		}
		return null;
	}

//		if(winners.size()>1&&deck.getSize()<winners.size()) {
//			return gameStatus.DRAW;
//		}
//		if(winners.size()>1) {
//			for (Player player : winners) {
//				player.addCardToHand(deck.draw());
//			}
//		}

	public void changeTurnForPlayers(Player currentPlayer) {
		currentPlayer.setTurn(false);
		if (players.size() > (players.indexOf(currentPlayer) + 1)) {
			players.get(players.indexOf(currentPlayer) + 1).setTurn(true);

		} else {
			players.get(0).setTurn(true);

		}
	}
	
	public Boolean confirmReplay() {
		counter++;
		if(counter == players.size()) {
			startNewGame();
			return true;
		}
		return false;
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
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public int getCounter() {
		return counter;
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

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void addGameIsDrawListener(GameIsDrawListener listener) {
		drawListeners.add(listener);
	}

	/*
	 * public void setPlayer(Player player) { if players.size() == 2) {
	 * players.add(player) whoWins() } else { players.add(player) }
	 *
	 * }
	 *
	 */

	/*
	 * public String whoWins() {
	 * 
	 * while (players.size() != 2) { try { Thread.sleep(500);
	 * System.out.println("HEEEEEEEJ"); } catch (InterruptedException e) {
	 * 
	 * // TODO Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * System.out.println(players.get(0).getGuess());
	 * System.out.println(players.get(1).getGuess()); if (players.get(0).getGuess()
	 * > players.get(1).getGuess()) {
	 * 
	 * return players.get(0).getName(); } else if (players.get(0).getGuess() <
	 * players.get(1).getGuess()) { return players.get(1).getName(); } else { return
	 * "Oavgjort"; }
	 * 
	 * }
	 */

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
