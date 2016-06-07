package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class Action {

	private final Player player;
	private final GameBoard gameBoard;

	public Action(Player player, GameBoard gameBoard) {
		this.player = player;
		this.gameBoard = gameBoard;
	}

	public Player getPlayer() {
		return player;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	// check if the player can do the action based on his "attributes"
	public abstract boolean isValid();

	public abstract TurnState execute(TurnState previousState);
}
