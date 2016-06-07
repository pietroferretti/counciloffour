package it.polimi.ingsw.ps14.model.actions.mainactions;

import java.util.List;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

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
		
		if (balcony.cardsMatch(cards))
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
