package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.actions.QuickAction;

public class CardDrawnState extends TurnState {

	public CardDrawnState() {
		// Nothing to initialize
	}

	@Override
	public boolean isActionValid(Action action) {
		return (action instanceof MainAction || action instanceof QuickAction) 
				&& action.isValid();
	}

}
