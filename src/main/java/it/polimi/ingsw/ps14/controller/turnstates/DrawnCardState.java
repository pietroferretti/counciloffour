package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.DrawCardAction;

public class DrawnCardState extends TurnState {

	private static final DrawnCardState INSTANCE = new DrawnCardState();

	private DrawnCardState() {
		// TODO Auto-generated constructor stub
	}

	public static DrawnCardState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {

		return !(action instanceof DrawCardAction) && action.isValid();
	}

}
