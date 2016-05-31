package it.polimi.ingsw.ps14.controller.actions.mainactions;

import it.polimi.ingsw.ps14.Balcony;
import it.polimi.ingsw.ps14.BusinessPermit;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticCard;
import it.polimi.ingsw.ps14.Region;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

import java.util.List;

public class AcquireBusinessPermiteTileAction extends MainAction {

	private Region region;
	private Balcony balcony;
	private BusinessPermit permitTile;
	private List<PoliticCard> cards;

	public AcquireBusinessPermiteTileAction(Player player, GameBoard gameBoard,
			TurnState previousState, Region region, BusinessPermit permitTile,
			List<PoliticCard> cards) {
		super(player, gameBoard, previousState);
		this.region = region;
		this.balcony = region.getBalcony();
		this.permitTile = permitTile;
		this.cards = cards;
	}

	@Override
	public boolean isValid() {

		if (!region.getBusinessPermits().BusinessPermitIsAvailable(permitTile))
			return false;
		// TODO: send ERROR in business permit choice
		if (balcony.cardsInBalcony(cards) == -1)
			return false;
		// TODO: send error: ERROR in color card choice
		if (super.getPlayer().getCoins() < balcony.councillorCost(cards))
			return false;
		// TODO: send ERROR: not enough coins
		if (!region.getBusinessPermits().cardIsFaceUp(permitTile))
			return false;
		// TODO: send ERROR: permitTile is not face up
		return true;
	}

	@Override
	public TurnState execute() {
		for (PoliticCard card : cards) {
			super.getPlayer().removeCard(card);
			super.getGameBoard().getPoliticDeck().addPoliticCard(card);
		}
		super.getPlayer().useCoins(balcony.councillorCost(cards));
		super.getPlayer().getBusinessHand().acquireBusinessPermit(permitTile);
		region.getBusinessPermits().substituteCard(permitTile);

		return nextState(super.getPreviousState());
	}
}
