package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.ColorCity;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;

public abstract class BuildEmporiumAction extends MainAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4096873819410368693L;
	
	public BuildEmporiumAction(Integer playerID) {
		super(playerID);
	}

	public void build(City city, Player player, Model model) {
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
