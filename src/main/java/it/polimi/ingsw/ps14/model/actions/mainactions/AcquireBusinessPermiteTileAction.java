package it.polimi.ingsw.ps14.model.actions.mainactions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class AcquireBusinessPermiteTileAction extends MainAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -385170414895517347L;
	private RegionType regionType;
	private Integer permitID;
	private List<PoliticCard> cards;

	public AcquireBusinessPermiteTileAction(Integer playerID, RegionType region, Integer permitID,
			List<PoliticCard> politicCards) {
		super(playerID);
		this.regionType = region;
		this.permitID = permitID;
		this.cards = politicCards;
	}

	@Override
	public boolean isValid(Model model) {
		Player player = model.id2player(super.getPlayer());
		Region region = model.getGameBoard().getRegion(regionType);
		Balcony balcony = region.getBalcony();
		BusinessPermit permitTile = model.id2permit(permitID, region);

		List<ColorPolitic> colors = new ArrayList<>();
		for (PoliticCard p : cards)
			colors.add(p.getColor());

		if (!player.hasPoliticCard(colors))
			return false;
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
	public TurnState execute(TurnState previousState, Model model) {

		Player player = model.id2player(super.getPlayer());
		Region region = model.getGameBoard().getRegion(regionType);
		BusinessPermit permitTile = model.id2permit(permitID, region);
		Balcony balcony = region.getBalcony();

		// pay councillors
		player.useCoins(balcony.councillorCost(cards));

		// remove politic cards used
		for (PoliticCard pc : cards)
			player.removeColor(pc.getColor());

		// add politic cards used to gameboard
		model.getGameBoard().getPoliticDeck().discardCards(cards);

		// acquire permit
		player.getBusinessHand().acquireBusinessPermit(permitTile);

		// change face up card in region
		region.getBusinessPermits().substituteCard(permitTile);
		// notifies changes in business deck
		region.setBusinessPermits();

		// TODO: bonus
		permitTile.getBonusList().useBonus(player, model);

		return nextState(previousState, model);
	}

}
