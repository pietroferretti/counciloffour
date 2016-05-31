package it.polimi.ingsw.ps14.controller.actions.quickactions;

import it.polimi.ingsw.ps14.Balcony;
import it.polimi.ingsw.ps14.ColorCouncillor;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class SendAssistantToElectCouncillorAction extends QuickAction {

	private final Balcony balcony;
	private final ColorCouncillor color;
	
	public SendAssistantToElectCouncillorAction(Player player, GameBoard gameBoard,TurnState previousState, 
													Balcony balcony, ColorCouncillor color) {
		super(player, gameBoard,previousState);
		this.balcony = balcony;
		this.color = color;
	}

	public boolean isValid() {
		return (super.getPlayer().getAssistants() >= 1
					&& super.getGameBoard().councillorIsAvailable(color)
					&& balcony != null
					&& color != null);
	}
	
	public TurnState execute() {
		super.getPlayer().useAssistants(1);
		super.getGameBoard().addAssistants(1);
		super.getGameBoard().addDiscardedCouncillor(balcony.electCouncillor(color));
		
		return nextState(super.getPreviousState()); 
	}
}
