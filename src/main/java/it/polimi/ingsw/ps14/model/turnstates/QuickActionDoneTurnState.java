package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;

public class QuickActionDoneTurnState extends TurnState {

	private static final long serialVersionUID = 3324340177325213924L;

	public QuickActionDoneTurnState(int additionalMains) {
		super(additionalMains);
	}

	@Override
	public TurnState makeCopy() {
		return new QuickActionDoneTurnState(super.additionalMains);
	}

	@Override
	public boolean isActionLegal(Action action, Model model) {
		return action instanceof MainAction && action.isValid(model);
	}

	@Override
	public String toString() {

		return "QuickActionDone";
	}
}
