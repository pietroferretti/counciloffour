package it.polimi.ingsw.ps14.model.actions.mainactions;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.actions.TurnAction;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class MainAction extends TurnAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1129134728564497045L;

	public MainAction(Integer playerID) {
		super(playerID);
	}

	@Override
	public boolean isValid(Model model) {
		return false;
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		return null;
	}

	public void useBonusNeighbors(City city, Player player, Model model) {
		List<City> cityVisited = new ArrayList<>();
		PriorityQueue<City> cityToken = new PriorityQueue<>();
		cityToken.add(city);
		int cont = 1;
		while (cont == 1) {
			city = cityToken.poll();
			cont = 0;
			for (City c : city.getNeighbors()) {
				if (c.isEmporiumBuilt(player) && !cityVisited.contains(c) && !cityToken.contains(c)) {
					c.getToken().useBonus(player, model);
					cityToken.add(c);
					cont = 1;
				}
			}
			cityVisited.add(city);
		}
	}



	protected TurnState nextState(TurnState previousState, Player player) {
		if (previousState instanceof CardDrawnState)
			return new MainActionDoneTurnState(player.additionalMainsToDo);
		if (previousState instanceof MainActionDoneTurnState) {
			player.additionalMainsToDo--;
			return new MainActionDoneTurnState(player.additionalMainsToDo);
		}
		if (previousState instanceof QuickActionDoneTurnState)
			return new MainAndQuickActionDoneTurnState(player.additionalMainsToDo);
		if (previousState instanceof MainAndQuickActionDoneTurnState) {
			player.additionalMainsToDo--;
			return new MainAndQuickActionDoneTurnState(player.additionalMainsToDo);
		}
		return null;
	}

}