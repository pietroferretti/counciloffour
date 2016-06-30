package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;

public class MainActionDoneTurnState extends TurnState {

	private static final long serialVersionUID = 8037639966267227577L;

	public MainActionDoneTurnState(int additionalMains) {
		super(additionalMains);
	}

	@Override
	public TurnState makeCopy() {
		return new MainActionDoneTurnState(super.additionalMains);
	}

	@Override
	public boolean isActionLegal(Action action, Model model) {
		return (action instanceof QuickAction || (action instanceof EndTurnAction && additionalMains == 0)
				|| (action instanceof MainAction && additionalMains > 0)) && action.isValid(model);
	}

	@Override
	public String toString() {
		return "MainActionDone";
	}

}
