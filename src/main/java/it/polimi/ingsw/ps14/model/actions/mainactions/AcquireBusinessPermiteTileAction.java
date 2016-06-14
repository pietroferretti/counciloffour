package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

import java.util.List;

public class AcquireBusinessPermiteTileAction extends MainAction {

	private RegionType regionType;
	private Balcony balcony;
	private Integer permitID;
	private List<PoliticCard> cards;

	public AcquireBusinessPermiteTileAction(Integer playerID, RegionType region, Integer permitID, List<PoliticCard> politicCards) {
		super(playerID);
		this.regionType = region;
		this.permitID = permitID;
		this.cards = politicCards;
	}

	@Override
	public boolean isValid(Model model) {
		Player player = id2player(super.getPlayer(), model);
		Region region = model.getGameBoard().getRegion(regionType);
		BusinessPermit permitTile = id2permit(permitID, region);
		
		if (!balcony.cardsMatch(cards))
			return false;
		// TODO: send error: ERROR in color choice
		if (player.getCoins() < balcony.councillorCost(cards))
			return false;
		// TODO: send ERROR: not enough coins
		if (!region.getBusinessPermits().cardIsFaceUp(permitTile))
			return false;
		// TODO: send ERROR: permitTile is not face up
		return true;
	}

	@Override
	public TurnState execute(TurnState previousState,Model model) {
		
		Player player = id2player(super.getPlayer(), model);
		Region region = model.getGameBoard().getRegion(regionType);
		BusinessPermit permitTile = id2permit(permitID, region);

		// pay councillors
		player.useCoins(balcony.councillorCost(cards));

		// remove cards politic used
		player.getHand().removeAll(cards);

		// add politic cards used to gameboard
		model.getGameBoard().getPoliticDeck().discardCards(cards);

		// acquire permit
		player.getBusinessHand().acquireBusinessPermit(permitTile);

		// change face up card in region
		region.getBusinessPermits().substituteCard(permitTile);

		// TODO: bonus
		permitTile.getBonus().useBonus(player, model);

		return nextState(previousState,player);
	}

}
