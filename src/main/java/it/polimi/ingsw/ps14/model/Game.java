package it.polimi.ingsw.ps14.model;

import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;

public class Game extends Observable implements Cloneable {
	private GameBoard gameBoard;
	private List<Player> players;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return null;
		// TODO Auto-generated method stub
		//return super.clone();
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public List<Player> getPlayers() {
		return players;
	}
}
