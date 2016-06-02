package it.polimi.ingsw.ps14.controller.actions.mainactions;

import java.util.List;

import it.polimi.ingsw.ps14.Balcony;
import it.polimi.ingsw.ps14.BusinessPermit;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticCard;
import it.polimi.ingsw.ps14.Region;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenAlreadyDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenNotDoneYetTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.DrawnCardState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenNotDoneYetTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.DrawnCardState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class AcquireBusinessPermiteTileAction extends MainAction {

	private Region region;
	private Balcony balcony;
	private BusinessPermit permitTile;
	private List<PoliticCard> cards;

	public AcquireBusinessPermiteTileAction(Player player, GameBoard gameBoard,
		 Region region, BusinessPermit permitTile,
			List<PoliticCard> cards) {
		super(player, gameBoard);
		this.region = region;
		this.balcony = region.getBalcony();
		this.permitTile = permitTile;
		this.cards = cards;
	}

	@Override
	public boolean isValid() {
		
		if (balcony.cardsInBalcony(cards)==-1)
			return false;
		// TODO: send error: ERROR in color choice
		if (super.getPlayer().getCoins() < balcony.councillorCost(cards))
			return false;
		//TODO: send ERROR: not enough coins 
		if (!region.getBusinessPermits().cardIsFaceUp(permitTile))
			return false;
		//TODO: send ERROR: permitTile is not face up
		return true;
	}

	@Override
	public TurnState execute(TurnState previousState) {
		
		//pay councillors
		super.getPlayer().useCoins(balcony.councillorCost(cards));
		
		// remove cards politic used
		super.getPlayer().getHand().removeAll(cards);

		// add politic cards used to gameboard
		super.getGameBoard().getPoliticDeck().discardCards(cards);
		
		//acquire permit
		super.getPlayer().getBusinessHand().acquireBusinessPermit(permitTile);
		
		//change face up card in region
		region.getBusinessPermits().substituteCard(permitTile);
		
		//TODO: bonus
		
		return nextState(previousState);
	}

}
