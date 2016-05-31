package it.polimi.ingsw.ps14.controller.actions.quickactions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public abstract class QuickAction extends Action {


	public QuickAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
	}
	
	protected TurnState nextState(TurnState previousState) {
		if (previousState instanceof CardDrawnState)
			return new QuickActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		if (previousState instanceof MainActionDoneTurnState)
			return new MainAndQuickActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		return null;
	}

}
