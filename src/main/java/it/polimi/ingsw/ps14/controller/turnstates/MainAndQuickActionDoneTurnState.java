package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.EndTurnAction;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.actions.QuickAction;

public class MainAndQuickActionDoneTurnState extends TurnState {

	public final int additionalMains;
	
	public MainAndQuickActionDoneTurnState(int additionalMains){
		this.additionalMains = additionalMains;
	}

	@Override
	public boolean isActionValid(Action action) {
		return ((action instanceof EndTurnAction && additionalMains == 0)
					|| (action instanceof MainAction && additionalMains > 0)
				) && action.isValid();
	}

}
