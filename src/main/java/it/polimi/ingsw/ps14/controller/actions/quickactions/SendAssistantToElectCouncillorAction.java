package it.polimi.ingsw.ps14.controller.actions.quickactions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.actions.QuickAction;
import it.polimi.ingsw.ps14.controller.turnstates.DrawnCardState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class SendAssistantToElectCouncillorAction extends QuickAction {

	public SendAssistantToElectCouncillorAction(Player player, GameBoard gameBoard,TurnState previousState) {
		super(player, gameBoard,previousState);
		// TODO Auto-generated constructor stub
	}

	private TurnState nextState(TurnState previousState) {
		if (previousState instanceof DrawnCardState)
			return QuickActionDoneTurnState.getInstance();
		if (previousState instanceof MainActionDoneTurnState)
			return MainAndQuickActionDoneTurnState.getInstance();
		return null;
	}
}
