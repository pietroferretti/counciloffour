package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.EndTurnAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.controller.actions.quickactions.QuickAction;

public class MainActionDoneTurnState extends TurnState {

	public MainActionDoneTurnState(int additionalMains) {
		super(additionalMains);
	}

	@Override
	public boolean isActionLegal(Action action) {
		return (action instanceof QuickAction 
					|| (action instanceof EndTurnAction && additionalMains == 0)
					|| (action instanceof MainAction && additionalMains > 0)
				) && action.isValid();
	}

}
