package it.polimi.ingsw.ps14;

import java.util.ArrayList;

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

	private Player findWinner() {
		/*
		 * Controlla tutti i giocatori per vedere chi ha vinto -->
		 * NobilityTrack: 5 punti al primo, 2 al secondo - se più giocatori sono
		 * primi, 5 punti solo a loro - se più giocatori sono secondi, 2 punti
		 * ciascuno --> BusinessPermits: 3 punti per il giocatore che ne ha
		 * comprati di più
		 * 
		 * Il giocatore con più punti vince.
		 */
		Player winner;

		// Create arrays to check nobility
		ArrayList<Player> highestNobility = new ArrayList<Player>();
		ArrayList<Player> secondHighNobility = new ArrayList<Player>();
		if (players.get(0).getLevel() > players.get(1).getLevel()) {
			highestNobility.add(players.get(0));
			secondHighNobility.add(players.get(1));
		} else if (players.get(0).getLevel() == players.get(1).getLevel()) {
			highestNobility.add(players.get(0));
			highestNobility.add(players.get(1));
		} else {
			highestNobility.add(players.get(1));
			secondHighNobility.add(players.get(0));
		}

		// Find players with first and second highest nobility
		for (int i = 2; i < players.size(); i++) {
			if (players.get(i).getLevel() > highestNobility.get(0).getLevel()) {
				highestNobility.clear();
				highestNobility.add(players.get(i));
			} else if (players.get(i).getLevel() == highestNobility.get(0).getLevel()) {
				highestNobility.add(players.get(i));
			} else if (players.get(i).getLevel() > secondHighNobility.get(0).getLevel()) {
				secondHighNobility.clear();
				secondHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() == secondHighNobility.get(0).getLevel()) {
				secondHighNobility.add(players.get(i));
			}
		}

		// Player with the highest nobility win points
		if (highestNobility.size() == 1) {
			highestNobility.get(0).addPoints(5);
			for (Player minorNoble : secondHighNobility) {
				minorNoble.addPoints(2);
			}
		} else {
			for (Player highestNoble : highestNobility) {
				highestNoble.addPoints(5);
			}
		}

		// Find players with the most permits
		ArrayList<Player> mostPermits = new ArrayList<Player>();
		mostPermits.add(players.get(0));
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getNumberOfPermits() > mostPermits.get(0).getNumberOfPermits()) {
				mostPermits.clear();
				mostPermits.add(players.get(i));
			} else if (players.get(i).getLevel() == mostPermits.get(0).getLevel()) {
				mostPermits.add(players.get(i));
			}
		}

		// Give points to the players with the most permits
		for (Player permitMaster : mostPermits) {
			permitMaster.addPoints(3);
		}

		// Find the winner by comparing points (and assistants and cards if
		// there's a draw)
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

		Player winner = findWinner();
		return winner;
		// print results somewhere
	}

}