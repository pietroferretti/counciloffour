package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class SendAssistantToElectCouncillorAction extends QuickAction {

	private final Balcony balcony;
	private final ColorCouncillor color;
	
	public SendAssistantToElectCouncillorAction(Player player, GameBoard gameBoard,
													Balcony balcony, ColorCouncillor color) {
		super(player, gameBoard);
		this.balcony = balcony;
		this.color = color;
	}

	public boolean isValid() {
		return (super.getPlayer().getAssistants() >= 1
					&& super.getGameBoard().councillorIsAvailable(color)
					&& balcony != null
					&& color != null);
	}
	
	public TurnState execute(TurnState previousState) {
		super.getPlayer().useAssistants(1);
		super.getGameBoard().addAssistants(1);
		super.getGameBoard().addDiscardedCouncillor(balcony.electCouncillor(color));
		
		return nextState(previousState); 
	}
}
