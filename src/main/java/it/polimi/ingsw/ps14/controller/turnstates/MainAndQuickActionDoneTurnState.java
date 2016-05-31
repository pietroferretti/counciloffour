package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.EndTurnAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;

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
