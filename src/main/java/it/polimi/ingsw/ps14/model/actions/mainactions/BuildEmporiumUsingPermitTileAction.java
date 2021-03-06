package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

/**
 * The player can build emporium using his unused business permit in a city
 * written in the business permit.
 * 
 *
 */
public class BuildEmporiumUsingPermitTileAction extends BuildEmporiumAction {

	private static final long serialVersionUID = 833335630529544205L;

	private final Integer businessCardID;
	private final String cityName;

	public BuildEmporiumUsingPermitTileAction(Integer playerID, Integer cardID, String city) {
		super(playerID);
		this.businessCardID = cardID;
		this.cityName = city;
	}

	@Override
	public boolean isValid(Model model) {
		Player player = model.id2player(super.getPlayerID());

		if (player == null)
			return false;

		BusinessPermit businessCard = model.id2permit(businessCardID, player);
		if (businessCard == null) // if player doesn't have card return null
			return false;

		City city = model.name2city(cityName);
		if (city == null)
			return false;

		// city is in the list of business permit selected
		if (!businessCard.contains(city))
			return false;

		// check if player has built in this city already
		if (city.isEmporiumBuilt(player))
			return false;

		// check if player has money enough to pay players that have built in
		// the city already
		if (city.numEmporiumsBuilt() > player.getAssistants())
			return false;

		return true;
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = model.id2player(super.getPlayerID());
		BusinessPermit businessCard = model.id2permit(businessCardID, player);
		City city = model.name2city(cityName);

		// use permit
		player.getBusinessHand().usePermit(businessCard);

		// give assistant back to gameboard (according to #Emporium built in
		// this city)
		player.useAssistants(city.numEmporiumsBuilt());

		// add them to gameboard
		player.addAssistants(city.numEmporiumsBuilt());
		
		build(city, player, model);

		// apply city token
		city.getToken().useBonus(player, model);

		// check bonus neighbors
		useNeighborsBonus(city, player, model);

		if (player.getNumEmporiums() == 10) {
			player.addPoints(3);
			return new EndTurnState();
		}

		return nextState(previousState, model);
	}
}
