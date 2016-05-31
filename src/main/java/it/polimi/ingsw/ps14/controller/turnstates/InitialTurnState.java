package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.DrawCardAction;

public class InitialTurnState extends TurnState {

	public InitialTurnState() {
		// Nothing to initialize
	}
	
	@Override
	public boolean isActionValid(Action action) {
		return action instanceof DrawCardAction && action.isValid();
	}

}
