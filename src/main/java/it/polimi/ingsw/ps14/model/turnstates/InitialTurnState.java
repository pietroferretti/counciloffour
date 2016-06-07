package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;

public class InitialTurnState extends TurnState {

	public InitialTurnState() {
		super(0);	// There can't be any additional main actions at the start of the turn
	}
	
	@Override
	public boolean isActionLegal(Action action) {
		return action instanceof DrawCardAction && action.isValid();
	}

}