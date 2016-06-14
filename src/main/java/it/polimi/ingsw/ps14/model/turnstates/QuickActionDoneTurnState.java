package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;

public class QuickActionDoneTurnState extends TurnState {

	public QuickActionDoneTurnState(int additionalMains) {
		super(additionalMains);
	}

	@Override
	public boolean isActionLegal(Action action, Model model) {
		return action instanceof MainAction && action.isValid(model);
	}

}
