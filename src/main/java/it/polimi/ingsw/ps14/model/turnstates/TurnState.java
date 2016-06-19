package it.polimi.ingsw.ps14.model.turnstates;

import java.io.Serializable;

import it.polimi.ingsw.ps14.exceptions.IllegalActionException;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;

public abstract class TurnState implements Serializable{
	

	private static final long serialVersionUID = -8394194912933055049L;
	
	public final int additionalMains;
	
	public TurnState(int additionalMains) {
		this.additionalMains = additionalMains;
	}
	
	/**
	 * Prototype Pattern!
	 */
	public abstract TurnState makeCopy();
	
	// check if we can do an action based on the TurnState we are in
	public abstract boolean isActionLegal(Action action, Model model);

	public TurnState executeAction(Action action, Model model) {
		
		if (!isActionLegal(action, model)) {
			throw new IllegalActionException("You cannot do this action now!");
		} 

		return action.execute(this, model);

	}
}
