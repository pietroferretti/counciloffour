package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;

public class MainAndQuickActionDoneTurnState extends TurnState {

	private static final MainAndQuickActionDoneTurnState INSTANCE = new MainAndQuickActionDoneTurnState();

	public static MainAndQuickActionDoneTurnState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {
		// TODO Auto-generated method stub
		return false;
	}

}
