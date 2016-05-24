package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.MainAction;

public class ChooseMainWhenAlreadyDoneTurnState extends TurnState {

	private static final ChooseMainWhenAlreadyDoneTurnState INSTANCE = new ChooseMainWhenAlreadyDoneTurnState();

	public static ChooseMainWhenAlreadyDoneTurnState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {

		return action.isValid() && action instanceof MainAction;
		
		/* alternativa a instanceof MainAction
		 * action instanceof ElectCouncillorAction || action instanceof
		 * AcquireBusinessPermiteTileAction || action instanceof
		 * BuildEmporiumUsingPermitTileAction || action instanceof
		 * BuildEmporiumWithHelpOfKingAction
		 */
	}

}
