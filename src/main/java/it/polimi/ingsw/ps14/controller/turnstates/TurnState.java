package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;

public abstract class TurnState {
	
	public final int additionalMains;
	
	public TurnState(int additionalMains) {
		this.additionalMains = additionalMains;
	}
	
	// check if we can do an action based on the TurnState we are in
	public abstract boolean isActionValid(Action action);

	public TurnState executeAction(Action action) {
		// check if the player can do the action based on his "attributes"
		if (!isActionValid(action)) {
			// TODO: definire ActionNotValidException
			throw new RuntimeException("You cannot do this action now!");
		}

		return action.execute();

	}
}
