package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.MainAction;

public class ChooseMainWhenNotDoneYetTurnState extends TurnState {

	private static final ChooseMainWhenNotDoneYetTurnState INSTANCE = new ChooseMainWhenNotDoneYetTurnState();

	public static ChooseMainWhenNotDoneYetTurnState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {

		return action.isValid() && action instanceof MainAction;
	}

}
