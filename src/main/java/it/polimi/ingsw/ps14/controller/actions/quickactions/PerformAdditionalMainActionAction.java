package it.polimi.ingsw.ps14.controller.actions.quickactions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class PerformAdditionalMainActionAction extends QuickAction {

	public PerformAdditionalMainActionAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
	}
	
	public boolean isValid() {
		return super.getPlayer().getAssistants() >= 3;
	}
	
	public TurnState execute(TurnState previousState) {
		super.getPlayer().useAssistants(3);
		super.getGameBoard().addAssistants(3);
		super.getPlayer().additionalMainsToDo++;
		
		return nextState(previousState); 
	}
}
