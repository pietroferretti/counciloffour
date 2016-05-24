package it.polimi.ingsw.ps14.controller.turnstates;

import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.ElectCouncillorAction;

public class ChooseMainWhenAlreadyDoneTurnState extends TurnState {

	private static final ChooseMainWhenAlreadyDoneTurnState INSTANCE = new ChooseMainWhenAlreadyDoneTurnState();

	public static ChooseMainWhenAlreadyDoneTurnState getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean isActionValid(Action action) {

		return action.isValid()
				&& (action instanceof ElectCouncillorAction || action instanceof AcquireBusinessPermiteTileAction
						|| action instanceof BuildEmporiumUsingPermitTileAction
						|| action instanceof BuildEmporiumWithHelpOfKingAction);

	}

}
