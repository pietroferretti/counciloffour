package it.polimi.ingsw.ps14.controller.actions.mainactions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.actions.Action;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenAlreadyDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenNotDoneYetTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.DrawnCardState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public abstract class MainAction extends Action {

	public MainAction(Player player, GameBoard gameBoard,TurnState previousState) {
		super(player, gameBoard,previousState);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TurnState execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected TurnState nextState(TurnState previousState) {
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
