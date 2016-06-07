package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;

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
