package it.polimi.ingsw.ps14.model;

import java.util.List;
import java.util.Observable;

public class Game extends Observable implements Cloneable {
	
	private GameBoard gameBoard;
	private List<Player> players;
	
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * 
	 * @param color
	 * @return player with that specific color
	 */
	// do per scontato che alla creazione di un giocatore gli venga assegnato un
	// colore univoco che usiamo come chiave
	public Player getIDPlayer(int id) {
		for (Player player : players) {
			if (player.getId()==id)
				return player;
		}
		// magari una exception?
		return null;
	}
	
	@Override
	public Game clone() throws CloneNotSupportedException{
		// TODO 
		throw new CloneNotSupportedException();
	}

}
