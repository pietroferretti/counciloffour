package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

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
