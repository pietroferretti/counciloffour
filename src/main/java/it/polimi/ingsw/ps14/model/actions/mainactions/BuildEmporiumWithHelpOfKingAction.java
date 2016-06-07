package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class BuildEmporiumWithHelpOfKingAction extends MainAction {

	private City city;
	private List<PoliticCard> cards;

	public BuildEmporiumWithHelpOfKingAction(Player player,
			GameBoard gameBoard, City city, List<PoliticCard> cards) {
		super(player, gameBoard);
		this.city = city;
		this.cards = cards;
	}

	public boolean isValid() {
		Balcony balcony = super.getGameBoard().getKing().getBalcony();

		if (balcony.cardsMatch(cards))
			return false;
		// TODO: send error: ERROR in color choice
		if (super.getPlayer().getCoins() < balcony.councillorCost(cards))
			return false;
		// TODO: send ERROR: not enough coins

		// player don't have built in the city yet
		if (city.isEmporiumBuilt(super.getPlayer()))
			return false;

		// check if player has money enough to pay players that have built in
		// the city yet
		if (super.getPlayer().getAssistants() < city.numEmporiumsBuilt())
			return false;

		// player has coins enough to move king
		City cityKing = super.getGameBoard().getKing().getCity();
		if (super.getPlayer().getCoins() < kingPathCost(cityKing, city))
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
	private Integer kingPathCost(City start, City stop) {
		Map<String, Integer> cost = new HashMap<>();
		PriorityQueue<String> queue = new PriorityQueue<>();
		City u;
		int costEachCity = 2;

		// controllo se start e stop coincidono
		if ((start.getName().compareTo(stop.getName())) == 0)
			return 0;
		else {

		//	 rendi tutti i vertici non marcati
			for (City city : super.getGameBoard().getCities()) {
				cost.put(city.getName(), null);
			}

			// marca il vertice s
			cost.put(start.getName(), 0);

			// enqueue s
			queue.add(start.getName());
			while (!queue.isEmpty()) {
				
				u = super.getGameBoard().getCityByName(queue.poll());
				for (City city : u.getNeighbors()) {

					if (cost.get((city.getName()))==null) {

						queue.add(city.getName());
						cost.replace(city.getName(), cost.get(u.getName())
								+ costEachCity);
					}
				}
				if (queue.contains(stop.getName())){
					//System.out.println(cost.get(stop.getName())); //Per TEST
					return cost.get(stop.getName());}
			}
			return null;
		}
	}

	public TurnState execute(TurnState previousState) {
		Balcony balcony = super.getGameBoard().getKing().getBalcony();
		City cityKing = super.getGameBoard().getKing().getCity();

		// remove coins to buy concillor
		super.getPlayer().useCoins(balcony.councillorCost(cards));

		// remove cards politic used
		super.getPlayer().getHand().removeAll(cards);

		// add politic cards used to gameboard
		super.getGameBoard().getPoliticDeck().discardCards(cards);

		// one assistant for each emporium that is built
		super.getPlayer().useAssistants(city.numEmporiumsBuilt());

		// pay to move king
		super.getPlayer().useCoins(kingPathCost(cityKing, city));

		// move king
		super.getGameBoard().getKing().setCity(city);

		// build emporium
		city.buildEmporium(super.getPlayer());

		// apply city token
		city.getToken().useBonus(super.getPlayer(),
				super.getGameBoard().getPoliticDeck());

		// TODO: bonus citta adiacenti

		return super.nextState(previousState);
	}
}
