package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.QuickAction;

public class MainActionDoneTurnState extends TurnState {

	private final static MainActionDoneTurnState INSTANCE = new MainActionDoneTurnState();

	public static MainActionDoneTurnState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {
		// TODO Auto-generated method stub
		return action.isValid() && action instanceof QuickAction;
	}

}
