package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.ColorCity;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

/**
 * The player can build emporium using his unused business permit in a city
 * written in the business permit.
 * 
 *
 */
public class BuildEmporiumUsingPermitTileAction extends MainAction {

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

		Player player = model.id2player(super.getPlayer());
		System.out.println(1);
		if (player == null)

			return false;
		System.out.println(2);
		BusinessPermit businessCard = model.id2permit(businessCardID, player);
		if (businessCard == null) // if player doesn't have card return null
			return false;
		System.out.println(3);
		City city = model.name2city(cityName);
		if (city == null)
			return false;
		System.out.println(4);
		// city is in the list of business permit selected
		if (!businessCard.contains(city))
			return false;
		System.out.println(5);
		// check if player has built in this city already
		if (city.isEmporiumBuilt(player))
			return false;
		System.out.println(6);
		// check if player has money enough to pay players that have built in
		// the city already
		if (city.numEmporiumsBuilt() > player.getAssistants())
			return false;

		return true;
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = model.id2player(super.getPlayer());
		BusinessPermit businessCard = model.id2permit(businessCardID, player);
		City city = model.name2city(cityName);

		// use permit
		player.getBusinessHand().usePermit(businessCard);

		// give assistant back to gameboard (according to #Emporium built in
		// this city)
		player.useAssistants(city.numEmporiumsBuilt());

		// add them to gameboard
		player.addAssistants(city.numEmporiumsBuilt());

		// build emporium in the city
		city.buildEmporium(player);

		Region region = city.getRegion();
		if ((region.getBonusRegion() != 0) && builtAllCitiesInRegion(player, region)) {
			player.addPoints(region.getBonusRegion());
			region.consumeBonusRegion();

			giveBonusKing(player, model.getGameBoard());
		}

		GameBoard gameboard = model.getGameBoard();
		ColorCity cityColor = city.getColor();
		if ((cityColor != ColorCity.PURPLE) && (gameboard.getColorBonus(cityColor) != 0)
				&& builtAllCitiesWithColor(player, gameboard, cityColor)) {
			player.addPoints(gameboard.useColorBonus(cityColor));

			giveBonusKing(player, gameboard);
		}

		// apply city token
		city.getToken().useBonus(player, model);

		// check bonus neighbors
		useBonusNeighbors(city, player, model);

		if (player.getNumEmporiums() == 10) {
			player.addPoints(3);
			return new EndTurnState();
		}

		return nextState(previousState, model);
	}

	private boolean builtAllCitiesInRegion(Player player, Region region) {
		boolean allBuilt = true;
		for (City cityInRegion : region.getCities()) {
			if (!cityInRegion.getEmporiums().contains(player)) {
				allBuilt = false;
			}
		}

		return allBuilt;
	}

	private boolean builtAllCitiesWithColor(Player player, GameBoard gameboard, ColorCity color) {
		boolean allBuilt = true;
		for (City cityInGameboard : gameboard.getCities()) {
			if (color.equals(cityInGameboard.getColor()) && !cityInGameboard.getEmporiums().contains(player)) {
				allBuilt = false;
			}
		}

		return allBuilt;
	}

	private void giveBonusKing(Player player, GameBoard gameboard) {
		if (gameboard.isKingBonusAvailable()) {
			player.addPoints(gameboard.useKingBonus());
		}
	}

}
