package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;

public class MainAndQuickActionDoneTurnState extends TurnState {

	public MainAndQuickActionDoneTurnState(int additionalMains){
		super(additionalMains);
	}

	@Override
	public boolean isActionLegal(Action action) {
		return ((action instanceof EndTurnAction && additionalMains == 0)
					|| (action instanceof MainAction && additionalMains > 0)
				) && action.isValid();
	}

}
