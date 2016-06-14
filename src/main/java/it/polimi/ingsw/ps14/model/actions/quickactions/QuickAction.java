package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.actions.TurnAction;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class QuickAction extends TurnAction {


	public QuickAction(Integer playerID) {
		super(playerID);
	}
	
	protected TurnState nextState(TurnState previousState,Model model) {
		Player player=super.id2player(getPlayer(), model);
		
		if (previousState instanceof CardDrawnState)
			return new QuickActionDoneTurnState(player.additionalMainsToDo);
		if (previousState instanceof MainActionDoneTurnState)
			return new MainAndQuickActionDoneTurnState(player.additionalMainsToDo);
		return null;
	}

}
