package it.polimi.ingsw.ps14.model;

import java.util.ArrayList;
import java.util.List;

//PROBABILE CONTROLLER

public class GameLogic {

	private GameBoard gameboard;

	private ArrayList<Player> players;
 
	private Settings settings;

	/*
	 * È GameLogic a passare il controllo da giocatore a giocatore? - magari
	 * passare nomi utenti invece dei Player --> creiamo i player qui
	 */
	public GameLogic(ArrayList<Player> players, Settings settings) {
		if (players.size() < 2) {
			throw new IllegalArgumentException("Too few players!");
		}
		this.players = players;
		this.settings = settings;

		this.gameboard = new GameBoard(settings); // Inizializzare la gameboard
													// con i
		// dati in settings
	}

	private boolean setupGame() {
		/*
		 * TODO: - scegli ordine giocatori - dai carte, monete, assistenti ai
		 * giocatori - popola balconi qui?
		 * 
		 * Ricordare: diversi setup a seconda del numero di giocatori
		 */
		return false;
	}

	private boolean round(Player player) {
		// returns true if the game is ending
		return false;
	}

	private void marketPhase() {
		// TODO
	}

	private void distributeEndGamePoints() {
		/*
		 * Assegna punti aggiuntivi ai giocatori alla fine del gioco
		 * NobilityTrack: 5 punti al primo, 2 al secondo - se più giocatori sono
		 * primi, 5 punti solo a loro - se più giocatori sono secondi, 2 punti
		 * ciascuno --> BusinessPermits: 3 punti per il giocatore che ne ha
		 * comprati di più
		 *
		 */

		// Find players with the highest (or second highest) nobility level
		List<List<Player>> highNobilityLists;
		List<Player> firstHighNobility;
		List<Player> secondHighNobility;
		
		highNobilityLists = findHighNobles();
		firstHighNobility = highNobilityLists.get(0);
		secondHighNobility = highNobilityLists.get(1);
		
		// Give points to the players with the highest nobility
		awardPointsNobility(firstHighNobility, secondHighNobility);

		// Find players with the most permits
		List<Player> mostPermits = findMostPermits();
		
		// Give points to the players with the most permits
		for (Player permitMaster : mostPermits) {
			permitMaster.addPoints(3);
		}
	}

	public Player playGame() {
		setupGame();

		boolean endgame = false;
		while (!endgame) {
			// TODO: Abbiamo bisogno di un modo per passare il controllo di
			// giocatore in giocatore...
			// TODO: cambiare leggermente il codice in modo che quando un
			// giocatore ha costruito 10 empori
			// ogni altro giocatore faccia un ulteriore turno di gioco, senza
			// market
			for (Player activePlayer : players) {
				endgame = round(activePlayer);
			}
			marketPhase();
		}

		distributeEndGamePoints();
		Player winner = findWinner();
		return winner;
		// print results somewhere
	}
	
	// I used a list of lists to return two values: 
	List<List<Player>> findHighNobles() {
		List<Player> firstHighNobility = new ArrayList<>();
		List<Player> secondHighNobility = new ArrayList<>();
		List<List<Player>> result = new ArrayList<>();
		
		// Initialize lists by checking the first two players
		if (players.get(0).getLevel() > players.get(1).getLevel()) {
			firstHighNobility.add(players.get(0));
			secondHighNobility.add(players.get(1));
		} else if (players.get(0).getLevel() == players.get(1).getLevel()) {
			firstHighNobility.add(players.get(0));
			firstHighNobility.add(players.get(1));
		} else {
			firstHighNobility.add(players.get(1));
			secondHighNobility.add(players.get(0));
		}

		// Find players with first and second highest nobility
		for (int i = 2; i < players.size(); i++) {
			if (players.get(i).getLevel() > firstHighNobility.get(0).getLevel()) {
				firstHighNobility.clear();
				firstHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() == firstHighNobility.get(0).getLevel()) {
				firstHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() > secondHighNobility.get(0).getLevel()) {
				secondHighNobility.clear();
				secondHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() == secondHighNobility.get(0).getLevel()) {
				secondHighNobility.add(players.get(i));
			}
		}
		
		result.add(firstHighNobility);
		result.add(secondHighNobility);
		return result;
	}

	void awardPointsNobility(List<Player> firstHighNobility, List<Player> secondHighNobility) {
		if (firstHighNobility.size() == 1) {
			firstHighNobility.get(0).addPoints(5);
			for (Player secondHighNoble : secondHighNobility) {
				secondHighNoble.addPoints(2);
			}
		} else {
			for (Player firstHighNoble : firstHighNobility) {
				firstHighNoble.addPoints(5);
			}
		}
	}
	
	List<Player> findMostPermits() {
		List<Player> mostPermits = new ArrayList<>();
		
		mostPermits.add(players.get(0));
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getBusinessHand().getNumberOfPermits() > mostPermits.get(0).getBusinessHand().getNumberOfPermits()) {
				mostPermits.clear();
				mostPermits.add(players.get(i));
			} else if (players.get(i).getLevel() == mostPermits.get(0).getLevel()) {
				mostPermits.add(players.get(i));
			}
		}
		
		return mostPermits;
	}
	
	void awardPointsPermits(List<Player> mostPermits) {
		for (Player mostPermitsPlayer : mostPermits) {
			mostPermitsPlayer.addPoints(3);
		}
	}

	// Find the winner by comparing points (and assistants and cards if
	// there's a draw)
	Player findWinner() {
		Player winner;
		
		winner = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getPoints() > winner.getPoints()) {
				winner = players.get(i);
			} else if (players.get(i).getPoints() == winner.getPoints()) {
				if (players.get(i).getAssistants() > winner.getAssistants()) {
					winner = players.get(i);
				} else if (players.get(i).getAssistants() == winner.getAssistants()) {
					if (players.get(0).getNumberOfCards() > winner.getNumberOfCards()) {
						winner = players.get(i);
					} else if (players.get(0).getNumberOfCards() == winner.getNumberOfCards()) {
						// TODO: se proprio c'è parità assoluta bisogna
						// riscriverlo in modo che ritorni una lista di
						// vincitori, in alternativa ritorna il primo per
						// default lol
					}
				}
			}
		}

		return winner;
	}
}