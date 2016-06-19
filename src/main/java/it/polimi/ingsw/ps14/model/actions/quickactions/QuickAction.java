package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.TurnAction;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class QuickAction extends TurnAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3321069405772489974L;

	public QuickAction(Integer playerID) {
		super(playerID);
	}

	protected TurnState nextState(TurnState previousState, Model model) {
		int addMains = model.getAdditionalMainsToDo();

		if (previousState instanceof CardDrawnState)
			return new QuickActionDoneTurnState(addMains);
		if (previousState instanceof MainActionDoneTurnState)
			return new MainAndQuickActionDoneTurnState(addMains);
		return null;
	}

}
