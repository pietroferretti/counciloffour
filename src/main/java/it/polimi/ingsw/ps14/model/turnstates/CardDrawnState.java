package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;

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
