package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;

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

		// every action is legit after drawing a card
		return true;
	}

}
