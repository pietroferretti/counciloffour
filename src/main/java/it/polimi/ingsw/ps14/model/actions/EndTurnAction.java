package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class EndTurnAction extends TurnAction {

	public EndTurnAction(Integer playerID) {
		super(playerID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(Model model) {
		// always valid :D
		return true;
	}

	@Override
	public TurnState execute(TurnState previousState,Model model) {
		return new EndTurnState();
	}

}
