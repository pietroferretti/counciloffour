package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class EngageAssistantAction extends QuickAction {

	public EngageAssistantAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
	}

	public boolean isValid() {
		return (super.getGameBoard().getAvailableAssistants() >= 1 
					&& super.getPlayer().getCoins() >= 3);
	}
	
	public TurnState execute(TurnState previousState) {
		super.getPlayer().useCoins(3);
		super.getGameBoard().useAssistants(1);
		super.getPlayer().addAssistants(1);
		
		return nextState(previousState); 
	}
}
