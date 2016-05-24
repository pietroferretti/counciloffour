package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;

public abstract class TurnState {
	
	// check if we can do an actin based on the TurnState we are in
	public abstract boolean isActionValid(Action action);

	public TurnState executeAction(Action action) {
		// check if the player can do the action based on his "attributes"
		if (!isActionValid(action)) {

			// TODO exception

		}

		return action.execute();

	}
}
