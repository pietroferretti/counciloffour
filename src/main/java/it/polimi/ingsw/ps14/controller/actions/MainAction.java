package it.polimi.ingsw.ps14.controller.actions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public abstract class MainAction extends Action {

	public MainAction(Player player, GameBoard gameBoard,TurnState previousState) {
		super(player, gameBoard,previousState);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TurnState execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
