package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class DrawCardAction extends TurnAction {

	public DrawCardAction(Integer playerID) {
		super(playerID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(Model model) {
		// always valid :D
		return true;
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player= id2player(super.getPlayer(),model);
		
		player.addPolitic(model.getGameBoard().getPoliticDeck().drawCard());
		player.additionalMainsToDo = 0;
		return new CardDrawnState();
	}


}
