package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.exceptions.IllegalActionException;
import it.polimi.ingsw.ps14.exceptions.InvalidActionException;

public abstract class TurnState {
	
	public final int additionalMains;
	
	public TurnState(int additionalMains) {
		this.additionalMains = additionalMains;
	}
	
	// check if we can do an action based on the TurnState we are in
	public abstract boolean isActionLegal(Action action);

	public TurnState executeAction(Action action) {
		
		if (!isActionLegal(action)) {
			throw new IllegalActionException("You cannot do this action now!");
		} else if (!action.isValid()) {
			throw new InvalidActionException();	// TODO Explain why it is invalid
		}

		return action.execute(this);

	}
}
