package it.polimi.ingsw.ps14.controller.actions.quickactions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class EngageAssistantAction extends QuickAction {

	public EngageAssistantAction(Player player, GameBoard gameBoard,TurnState previousState) {
		super(player, gameBoard,previousState);
	}

	public boolean isValid() {
		return (super.getGameBoard().getAvailableAssistants() >= 1 
					&& super.getPlayer().getCoins() >= 3);
	}
	
	public TurnState execute() {
		super.getPlayer().useCoins(3);
		super.getGameBoard().useAssistants(1);
		super.getPlayer().addAssistants(1);
		
		return nextState(super.getPreviousState()); 
	}
}
