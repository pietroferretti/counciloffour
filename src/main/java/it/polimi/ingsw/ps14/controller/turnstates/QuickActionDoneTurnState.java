package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.MainAction;

public class QuickActionDoneTurnState extends TurnState {

	public final int additionalMains;

	public QuickActionDoneTurnState(int additionalMains) {
		this.additionalMains = additionalMains;
	}

	@Override
	public boolean isActionValid(Action action) {
		return action instanceof MainAction && action.isValid();
	}

}
