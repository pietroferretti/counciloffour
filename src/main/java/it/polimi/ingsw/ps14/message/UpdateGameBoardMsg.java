package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.GameBoard;

public class UpdateGameBoardMsg {
	private GameBoard gameBoard;

	public UpdateGameBoardMsg(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}
}
