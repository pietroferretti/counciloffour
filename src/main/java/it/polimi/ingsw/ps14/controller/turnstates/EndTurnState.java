package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;

public class EndTurnState extends TurnState {

	public EndTurnState() {
		super(0);	// There can't be any more main actions
	}

	@Override
	public boolean isActionLegal(Action action) {
		// No actions allowed
		return false;
	}

}
