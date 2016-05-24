package it.polimi.ingsw.ps14.controller.actions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public abstract class Action {

	Player player;
	GameBoard gameBoard;

	public Action(Player player, GameBoard gameBoard) {
		this.player = player;
		this.gameBoard = gameBoard;
	}

	// check if the player can do the action based on his "attributes"
	public abstract boolean isValid();

	public abstract TurnState execute();
}
