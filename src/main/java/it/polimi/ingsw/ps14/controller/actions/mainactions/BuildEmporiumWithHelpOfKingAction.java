package it.polimi.ingsw.ps14.controller.actions.mainactions;

import it.polimi.ingsw.ps14.BusinessPermit;
import it.polimi.ingsw.ps14.City;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BuildEmporiumWithHelpOfKingAction extends MainAction {
	
	private City city;
	private BusinessPermit businessCard;

	public BuildEmporiumWithHelpOfKingAction(Player player,
			GameBoard gameBoard, City city,
			BusinessPermit businessCard) {
		super(player, gameBoard);
		this.city = city;
		this.businessCard = businessCard;
	}



	/**
	 * Calcolate minimum path that king has to do
	 * 
	 * @param start
	 *            where king is now
	 * @param stop
	 *            where king wants to go
	 * @return coin that player must pay to move the king
	 */
//	private int kingPathCost(City start, City stop) {
//		int cost = 0;
//		Map<City, Color> visitCity = new HashMap<>();
//		List<City> neighbors = new ArrayList<>();
//
//		for (City city : super.getGameBoard().getCities()) {
//			if (!visitCity.containsKey(city))
//				visitCity.put(city, Color.WHITE);
//		}
//
//		for (City cy : start.getNeighbors()) {
//			visitCity.put(start, Color.GRAY);
//			neighbors.add(cy);
//			if (city.equals(stop))
//				return cost;
//		}
//		visitCity.put(start, Color.BLACK);
//		for (City city : neighbors) {
//			neighbors.remove(city);
//
//		}
		// ---------------------------------------

//		neighbors.add(start);
//		Iterator it=visitCity.entrySet().iterator();
//		while(it.hasNext() && )
//
//			for (City cy : str.getNeighbors()) {
//				if (!visitCity.containsKey(cy)) {
//					visitCity.put(cy, Color.GRAY);
//				}
//			}
//			visitCity.put(str, Color.BLACK);
//			
//			
//			for (City c : neighbors)
//				if (city.equals(stop))
//					return cost;
//		}
//
//		//
//		//
//		//
//		// for (City city : cy.getNeighbors()) {
//		// if (city.equals(stop))
//		// return cost;
//		// }
//		// visitCity.put(cy, Color.BLACK);
//
//		return 0;
//	}
}
