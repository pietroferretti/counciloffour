package it.polimi.ingsw.ps14.controller.actions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class EndTurnAction extends Action {

	public EndTurnAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// always valid :D
		return true;
	}

	@Override
	public TurnState execute(TurnState previousState) {
		return new EndTurnState();
	}

}
