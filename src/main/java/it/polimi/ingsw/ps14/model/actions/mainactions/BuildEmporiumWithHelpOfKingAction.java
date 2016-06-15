package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BuildEmporiumWithHelpOfKingAction extends MainAction {

	private String cityName;
	private List<PoliticCard> cards;

	public BuildEmporiumWithHelpOfKingAction(Integer playerID, String city,
			List<PoliticCard> cards) {
		super(playerID);
		this.cityName = city;
		this.cards = cards;
	}

	public boolean isValid(Model model) {
		Player player = id2player(super.getPlayer(), model);
		City city = id2city(cityName, model);

		Balcony balcony = model.getGameBoard().getKing().getBalcony();

		if (!balcony.cardsMatch(cards))
			return false;
		// TODO: send error: ERROR in color choice
		if (player.getCoins() < balcony.councillorCost(cards))
			return false;
		// TODO: send ERROR: not enough coins

		// player don't have built in the city yet
		if (city.isEmporiumBuilt(player))
			return false;

		// check if player has money enough to pay players that have built in
		// the city yet
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
		PriorityQueue<String> queue = new PriorityQueue<>();
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
						cost.replace(city.getName(), cost.get(u.getName())
								+ costEachCity);
					}
				}
				if (queue.contains(stop.getName())) {
					// System.out.println(cost.get(stop.getName())); //Per TEST
					return cost.get(stop.getName());
				}
			}
			return null;
		}
	}

	public TurnState execute(TurnState previousState, Model model) {

		Player player = id2player(super.getPlayer(), model);
		City city = id2city(cityName, model);

		Balcony balcony = model.getGameBoard().getKing().getBalcony();
		City cityKing = model.getGameBoard().getKing().getCity();

		// remove coins to buy concillor
		player.useCoins(balcony.councillorCost(cards));

		// remove cards politic used
		player.getHand().removeAll(cards);

		// add politic cards used to gameboard
		model.getGameBoard().getPoliticDeck().discardCards(cards);

		// one assistant for each emporium that is built
		player.useAssistants(city.numEmporiumsBuilt());

		// pay to move king
		player.useCoins(kingPathCost(cityKing, city, model));

		// move king
		model.getGameBoard().getKing().setCity(city);

		// build emporium
		city.buildEmporium(player);

		// apply city token
		city.getToken().useBonus(player, model);

		// check bonus neighbors
		useBonusNeighbors(city,player,model);

		if (player.getNumEmporiums() == 10) {
			player.addPoints(3);
			return new EndTurnState();
		}

		return super.nextState(previousState,player);
	}

}
