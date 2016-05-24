package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.DrawCardAction;

public class InitialTurnState extends TurnState {

	private static final InitialTurnState INSTANCE = new InitialTurnState();

	public static InitialTurnState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {

		return action instanceof DrawCardAction && action.isValid();
	}

}
