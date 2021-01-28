package com.yrgo.sp.cardgame.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.Timer;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author elske, ptemrz, pontus, simon.
 * Entity for Game class, which contains all methods to play a game.
 * TODO: this is not an entity...
 *
 */
public class Game implements ActionListener {

	@JsonIgnore
	private Deck deck;

	private int nGames = 0;
	private int turns;
	private List<Player> players;
	private long id;
	private List<MappedCard> table;
	private LinkedList<MappedCard> muck;// Slängda kort aka slasken
	private List<KlimatkollListener> gameListener = new ArrayList<KlimatkollListener>();
	private List<InactivityListener> inactivityListeners = new ArrayList<>();
	private int replayCounter = 0;
	private Timer timer;
	private Timer inactivityTimer;
	private int turnTime = 40000; // 40 sek i millisek
	private int timeBeforeInactive = 5000;//1000 * 60 * 5; // 5 minuter

	@JsonIgnore
	private int minCards;

	/**
	 * Constructor for Game
	 * @param id
	 * @param numberOfPlayers
	 */
	public Game(long id, int numberOfPlayers) {
		this.players = new ArrayList<Player>();// Skapa arraylist med storleken satt till antal spelare
		for (int i = 0; i < numberOfPlayers; i++) {
			players.add(null);
		}
		this.minCards = numberOfPlayers * 3 + 2;
		this.table = new ArrayList<MappedCard>();
		this.muck = new LinkedList<MappedCard>();

		// skapar en deck som fylls med kort i Decks konstruktor
		this.deck = new Deck();
		this.id = id;
		timer = new Timer(turnTime, this);
		inactivityTimer = new Timer(timeBeforeInactive, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(InactivityListener l: inactivityListeners) {
					l.hasGoneInactive(id);
				}
			}
			
		});
		inactivityTimer.setRepeats(false);
	}


	/**
	 * Method to start a new game.
	 * When method is called it first clears the table and the muck pile (cards that weren't put in the correct position on the table).
	 * It then draws two cards that will be laid open on the table in the correct order (scorewise).
	 * Then it draws three cards for each player which will be added to their respective hand.
	 */
	public void startNewGame() {
		this.inactivityTimer.restart();
		this.table.clear();
		this.muck.clear();
		turns = 0;

		// Draw two cards that will be on the table at the start of the game
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

	/**
	 * Method to add a player to a game
	 * @param name
	 */
	public void addPlayer(String name) {
		try {
			players.set(players.indexOf(null), new Player(name));
		} catch (IndexOutOfBoundsException e) {
			// Players are full
		}
	}



	/**
	 * Method to find a card on the table by its ID
	 * @param id
	 * @return Mapped Card
	 */
	public MappedCard findCardInTableById(long id) {
		Optional<MappedCard> c = table.stream().filter(ca -> ca.getId() == id).findFirst();
		return c.get();
	}

	/**
	 * Adds a card to the table if the card was correctly placed, otherwise it ends
	 * up in the muck.
	 *
	 * @param player
	 * @param cardId
	 * @param index  of the placement of the new card.
	 * @return A boolean indicating if the move was correct.
	 */
	public boolean makeMove(Player player, long cardId, int index) {
		if (!player.isTurn()) {// Exception av slag här va? Det var inte den här spelarens tur!
			// return null;
		}

		timer.stop();
		player.resetMissedTurns();

		Optional<MappedCard> pc = player.getHand()
				.stream()
				.filter(card -> card.getId() == cardId)
				.findFirst();

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
				timer.restart();
				return false;
			} catch (IllegalArgumentException e) {
				// Deck is empty
				// Game is a DRAW
				for (KlimatkollListener listener : gameListener) {
					listener.gameIsDraw(this);
				}
				return false;
			}
		}
		timer.restart();
		this.inactivityTimer.restart();
		return true;
	}

	/**
	 * Checks if a players hand is empty and thus is the winner. If more than one
	 * player places their last card during the same turn, these players draws one
	 * more card. If the draw pile depletes the game ends as a draw.
	 *
	 * @return The name of the winner
	 */
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

						for (KlimatkollListener listener : gameListener) {
							listener.gameIsDraw(this);
						}
						nGames++;
						timer.stop();
						return "Oavgjort";
					}
				}
				return null;
			} else if (!winners.isEmpty()) {
				winners.get(0).addWin();
				nGames++;
				timer.stop();
				return winners.get(0).getName();
			}
		}
		return null;
	}

	/**
	 * Method to change the turn to the other player
	 */
	public void changeTurnForPlayers() {
		Player currentPlayer = getCurrentPlayer();
		currentPlayer.setTurn(false);

		if (players.size() > (players.indexOf(currentPlayer) + 1)) {
			players.get(players.indexOf(currentPlayer) + 1).setTurn(true);

		} else {
			players.get(0).setTurn(true);

		}
	}

	/**
	 * Method that keeps track of the question if all players have confirmed that they want to replay after a game
	 * if all players have confirmed, it returns true, starts a new game and resets the replaycounter variable, otherwise it returns false.
	 * @return boolean for replay
	 */
	public Boolean confirmReplay() {
		replayCounter++;
		if (replayCounter == players.size()) {
			startNewGame();
			replayCounter = 0;
			return true;
		}
		return false;
	}

	public void addGameListener(KlimatkollListener listener) {
		gameListener.add(listener);
	}

	public Player getCurrentPlayer() {
		return players.stream().filter(pl -> pl.isTurn() == (true)).findFirst().get();
	}

	/**
	 * This method executes when the timer reaches its 40 seconds mark. The turn
	 * shifts to the next player. After a player misses three consecutive turns they
	 * lose.
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("TIMER AKTIVERAS!");
		System.out.println("Game id: " + Game.this.id);
		Player currentPlayer = getCurrentPlayer();
		currentPlayer.addMissedTurn();
		if (currentPlayer.getMissedTurns() >= 3) {// Har spelaren har missat sin tur 3 ggr i rad?
			timer.stop();
			currentPlayer.resetMissedTurns();
			Player p = players.stream().filter(pl -> !pl.equals(currentPlayer)).findFirst().get();
			// Hämtar den som inte gjort walkover.
			// Funkar bara för två spelare
			for (KlimatkollListener listener : gameListener) {
				listener.walkover(this, p);
			}
		}
		changeTurnForPlayers();
		for (KlimatkollListener listener : gameListener) {
			listener.timerRunOut(Game.this);
		}

	}

	// Getter and Setter methods

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public int getNumberOfGames() {
		return nGames;
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
		this.replayCounter = counter;
	}

	public int getCounter() {
		return replayCounter;
	}

	public int getMinCards() {
		return this.minCards;
	}

	public List<MappedCard> getTable() {
		return this.table;
	}

	public List<MappedCard> getMuck() {
		return muck;
	}


	public void addInactivityListener(InactivityListener listener) {
		this.inactivityListeners.add(listener);
		
	}

}
