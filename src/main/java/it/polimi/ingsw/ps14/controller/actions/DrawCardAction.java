package it.polimi.ingsw.ps14.controller.actions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.controller.turnstates.*;

public class DrawCardAction extends Action {

	public DrawCardAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// always valid :D
		return true;
	}

	@Override
	public TurnState execute(TurnState previousState) {
		super.getPlayer().addPolitic(super.getGameBoard().getPoliticDeck().drawCard());
		super.getPlayer().additionalMainsToDo = 0;
		return new CardDrawnState();
	}

}
