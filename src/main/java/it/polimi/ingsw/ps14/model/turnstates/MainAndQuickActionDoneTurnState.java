package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;

public class MainAndQuickActionDoneTurnState extends TurnState {

	private static final long serialVersionUID = -6896372820796392456L;

	public MainAndQuickActionDoneTurnState(int additionalMains) {
		super(additionalMains);
	}

	@Override
	public TurnState makeCopy() {
		return new MainAndQuickActionDoneTurnState(super.additionalMains);
	}

	@Override
	public boolean isActionLegal(Action action, Model model) {
		return ((action instanceof EndTurnAction && additionalMains == 0)
				|| (action instanceof MainAction && additionalMains > 0)) && action.isValid(model);
	}

	@Override
	public String toString() {
		return "MainAndQuickActionDone";
	}
}
