package it.polimi.ingsw.ps14.model.turnstates;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.QuickAction;

public class CardDrawnState extends TurnState {

	private static final long serialVersionUID = -1990794271096293430L;

	public CardDrawnState() {
		super(0); // There can't be any additional main actions at the start of
					// the turn
	} 

	@Override
	public TurnState makeCopy() {
		return new CardDrawnState();
	}

	@Override
	public boolean isActionLegal(Action action, Model model) {
		return (action instanceof MainAction || action instanceof QuickAction) && action.isValid(model);
	}

	@Override
	public String toString() {
		return "Card Drawn";
	}
}
