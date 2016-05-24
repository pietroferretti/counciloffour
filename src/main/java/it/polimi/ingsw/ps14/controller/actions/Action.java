package it.polimi.ingsw.ps14.controller.actions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public abstract class Action {

	private Player player;
	private GameBoard gameBoard;
	private TurnState previousState;

	public Action(Player player, GameBoard gameBoard, TurnState previousState) {
		this.player = player;
		this.gameBoard = gameBoard;
		this.previousState=previousState;
	}

	public TurnState getPreviousState() {
		return previousState;
	}

	public void setPreviousState(TurnState previousState) {
		this.previousState = previousState;
	}

	public Player getPlayer() {
		return player;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	// check if the player can do the action based on his "attributes"
	public abstract boolean isValid();

	public abstract TurnState execute();
}
