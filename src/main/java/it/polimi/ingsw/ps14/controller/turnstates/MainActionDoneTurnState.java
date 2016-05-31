package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.EndTurnAction;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.actions.QuickAction;

public class MainActionDoneTurnState extends TurnState {

	public final int additionalMains; 
	
	public MainActionDoneTurnState(int additionalMains) {
		this.additionalMains = additionalMains;
	}

	@Override
	public boolean isActionValid(Action action) {
		return (action instanceof QuickAction 
					|| (action instanceof EndTurnAction && additionalMains == 0)
					|| (action instanceof MainAction && additionalMains > 0)
				) && action.isValid();
	}

}
