package it.polimi.ingsw.ps14.controller.actions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

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
