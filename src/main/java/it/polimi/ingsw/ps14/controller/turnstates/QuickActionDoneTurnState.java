package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.MainAction;

public class QuickActionDoneTurnState extends TurnState {

	private static final QuickActionDoneTurnState INSTANCE = new QuickActionDoneTurnState();

	public static QuickActionDoneTurnState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {
		return action.isValid() && action instanceof MainAction;
	}

}
