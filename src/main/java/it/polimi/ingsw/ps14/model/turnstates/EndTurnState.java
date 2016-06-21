package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;

public class EndTurnState extends TurnState {

	public EndTurnState() {
		super(0); // There can't be any more main actions
	}

	@Override
	public TurnState makeCopy() {
		return new EndTurnState();
	}

	@Override
	public boolean isActionLegal(Action action, Model model) {
		// No actions allowed
		return false;
	}

	@Override
	public String toString() {
		return "EndTurn";
	}

}
