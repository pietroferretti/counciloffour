package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class MainAction extends Action {

	public MainAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TurnState execute(TurnState previousState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected TurnState nextState(TurnState previousState) {
		if (previousState instanceof CardDrawnState)
			return new MainActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		if (previousState instanceof MainActionDoneTurnState) {
			super.getPlayer().additionalMainsToDo--;
			return new MainActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		}
		if (previousState instanceof QuickActionDoneTurnState)
			return new MainAndQuickActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		if (previousState instanceof MainAndQuickActionDoneTurnState) {
			super.getPlayer().additionalMainsToDo--;
			return new MainAndQuickActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		}
		return null;
	}

}