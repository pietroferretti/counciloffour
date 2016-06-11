package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class EndTurnAction extends TurnAction {

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
