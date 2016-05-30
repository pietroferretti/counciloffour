package it.polimi.ingsw.ps14.controller.actions.mainactions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenAlreadyDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenNotDoneYetTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.DrawnCardState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class BuildEmporiumUsingPermitTileAction extends MainAction {

	public BuildEmporiumUsingPermitTileAction(Player player, GameBoard gameBoard, TurnState previousState) {
		super(player, gameBoard,previousState);
		// TODO Auto-generated constructor stub
		//TODO check bonus collegati a tutte le città (funzione ricorsiva?)
	}

	private TurnState nextState(TurnState previousState) {
		if (previousState instanceof DrawnCardState)
			return MainActionDoneTurnState.getInstance();
		if (previousState instanceof ChooseMainWhenNotDoneYetTurnState)
			return QuickActionDoneTurnState.getInstance();
		if ((previousState instanceof QuickActionDoneTurnState)
			|| (previousState instanceof ChooseMainWhenAlreadyDoneTurnState))
			return MainAndQuickActionDoneTurnState.getInstance();
		return null;
	}
	
}
