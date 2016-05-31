package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;

public class QuickActionDoneTurnState extends TurnState {

	public QuickActionDoneTurnState(int additionalMains) {
		super(additionalMains);
	}

	@Override
	public boolean isActionValid(Action action) {
		return action instanceof MainAction && action.isValid();
	}

}
