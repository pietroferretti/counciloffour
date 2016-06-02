package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.controller.actions.quickactions.QuickAction;

public class CardDrawnState extends TurnState {

	public CardDrawnState() {
		super(0);	// There can't be any additional main actions at the start of the turn
	}

	@Override
	public boolean isActionLegal(Action action) {
		return (action instanceof MainAction || action instanceof QuickAction) 
				&& action.isValid();
	}

}
