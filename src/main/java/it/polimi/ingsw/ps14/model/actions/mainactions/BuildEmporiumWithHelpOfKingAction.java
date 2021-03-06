package it.polimi.ingsw.ps14.model.actions.mainactions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

/**
 * 
 * Build an emporium corrupting king's conucillors . The player must specified
 * city where to build and politic cards to corrupt king's council
 *
 */
public class BuildEmporiumWithHelpOfKingAction extends BuildEmporiumAction {

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(BuildEmporiumWithHelpOfKingAction.class.getName());
	private static final long serialVersionUID = 4780505443771942777L;
	private String cityName;
	private List<PoliticCard> cards;

	public BuildEmporiumWithHelpOfKingAction(Integer playerID, String city, List<PoliticCard> cards) {
		super(playerID);
		this.cityName = city;
		this.cards = cards;
	}

	public boolean isValid(Model model) {

		Player player = model.id2player(super.getPlayerID());
		City city = model.name2city(cityName);
		Balcony balcony = model.getGameBoard().getKing().getBalcony();

		if (player == null || city == null || balcony == null) {
			LOGGER.info(String.format("isValid conversion error"));
			return false;
		}

		if (!balcony.cardsMatch(cards))
			return false;

		if (player.getCoins() < balcony.councillorCost(cards))
			return false;

		// player hasn't built in the city yet
		if (city.isEmporiumBuilt(player))
			return false;

		// check if player has money enough to pay players that have built in
		// the city already
		if (player.getAssistants() < city.numEmporiumsBuilt())
			return false;

		// player has coins enough to move king
		City cityKing = model.getGameBoard().getKing().getCity();
		if (player.getCoins() < kingPathCost(cityKing, city, model))
			return false;

		return true;
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
	private Integer kingPathCost(City start, City stop, Model model) {
		Map<String, Integer> cost = new HashMap<>();
		Queue<String> queue = new LinkedList<>();
		City u;
		int costEachCity = 2;

		// controllo se start e stop coincidono
		if ((start.getName().compareTo(stop.getName())) == 0)
			return 0;
		else {

			// rendi tutti i vertici non marcati
			for (City city : model.getGameBoard().getCities()) {
				cost.put(city.getName(), null);
			}

			// marca il vertice s
			cost.put(start.getName(), 0);

			// enqueue s
			queue.add(start.getName());
			while (!queue.isEmpty()) {

				u = model.getGameBoard().getCityByName(queue.poll());
				for (City city : u.getNeighbors()) {

					if (cost.get((city.getName())) == null) {

						queue.add(city.getName());
						cost.replace(city.getName(), cost.get(u.getName()) + costEachCity);
					}
				}
				if (queue.contains(stop.getName())) {
					return cost.get(stop.getName());
				}
			}
			return null;
		}
	}

	public TurnState execute(TurnState previousState, Model model) {

		Player player = model.id2player(super.getPlayerID());
		City city = model.name2city(cityName);

		Balcony balcony = model.getGameBoard().getKing().getBalcony();
		City cityKing = model.getGameBoard().getKing().getCity();

		if (player == null || city == null || balcony == null || cityKing == null) {
			LOGGER.info(String.format("execute conversion error"));
			return null;
		}

		// remove coins to buy concillor
		player.useCoins(balcony.councillorCost(cards));

		// remove politic cards used
		for (PoliticCard pc : cards)
			player.removeColor(pc.getColor());

		// add politic cards used to gameboard
		model.getGameBoard().getPoliticDeck().discardCards(cards);

		// one assistant for each emporium that is built
		player.useAssistants(city.numEmporiumsBuilt());
		
		// add them to gameboard
		player.addAssistants(city.numEmporiumsBuilt());

		// pay to move king
		player.useCoins(kingPathCost(cityKing, city, model));

		// move king
		model.getGameBoard().getKing().setCity(city);

		build(city, player, model);

		// apply city token
		if (city.getToken() != null)
			city.getToken().useBonus(player, model);

		// check bonus neighbors
		useNeighborsBonus(city, player, model);

		if (player.getNumEmporiums() == 10) {
			player.addPoints(3);
			return new EndTurnState();
		}

		return super.nextState(previousState, model);
	}

}
