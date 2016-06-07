package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class ChangeBusinessPermitTilesAction extends QuickAction {

	private final Region region;
	
	public ChangeBusinessPermitTilesAction(Player player, GameBoard gameBoard, Region region) {
		super(player, gameBoard);
		this.region = region;
	}

	public boolean isValid() {
		return (region.getBusinessPermits().cardsLeftInDeck() > 0
					&& super.getPlayer().getAssistants() >= 1
					&& region != null);
	}
	
	@Override
	public TurnState execute(TurnState previousState) {
		super.getPlayer().useAssistants(1);
		super.getGameBoard().addAssistants(1);
		
		BusinessPermit[] permitArray = region.getBusinessPermits().getAvailablePermits();
		for (BusinessPermit card : permitArray) {
			region.getBusinessPermits().removeBusinessPermit(card);
			region.getBusinessPermits().addCardToDeckBottom(card);
		}
		region.getBusinessPermits().fillEmptySpots();
		
		return nextState(previousState);
	}
	
}
