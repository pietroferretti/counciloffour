package it.polimi.ingsw.ps14.controller.actions.quickactions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.Region;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

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
		// TODO aggiungere "metti carte in fondo al mazzo" al deck di business permit in regione
		
		return nextState(previousState);
	}
	
}
