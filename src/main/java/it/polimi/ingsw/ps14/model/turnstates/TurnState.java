package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.exceptions.IllegalActionException;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;

public abstract class TurnState {
	
	public final int additionalMains;
	
	public TurnState(int additionalMains) {
		this.additionalMains = additionalMains;
	}
	
	// check if we can do an action based on the TurnState we are in
	public abstract boolean isActionLegal(Action action, Model model);

	public TurnState executeAction(Action action, Model model) {
		
		if (!isActionLegal(action, model)) {
			throw new IllegalActionException("You cannot do this action now!");
		} 

		return action.execute(this, model);

	}
}
