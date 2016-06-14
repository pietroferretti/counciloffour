package it.polimi.ingsw.ps14.model.actions.mainactions;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.actions.TurnAction;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class MainAction extends TurnAction {

	public MainAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TurnState execute(TurnState previousState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void useBonusNeighbors(City city) {
		List<City> cityVisited = new ArrayList<>();
		PriorityQueue<City> cityToken = new PriorityQueue<>();
		cityToken.add(city);
		int cont = 1;
		while (cont == 1) {
			city=cityToken.poll();
			cont=0;
			for (City c : city.getNeighbors()) {
				if (c.isEmporiumBuilt(super.getPlayer()) && !cityVisited.contains(c) && !cityToken.contains(c)) {
					c.getToken().useBonus(super.getPlayer(),super.getGameBoard());
					cityToken.add(c);
					cont=1;
				}
			}
			cityVisited.add(city);
		}
	}
	
	protected TurnState nextState(TurnState previousState) {
		if (previousState instanceof CardDrawnState)
			return new MainActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		if (previousState instanceof MainActionDoneTurnState) {
			super.getPlayer().additionalMainsToDo--;
			return new MainActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		}
		if (previousState instanceof QuickActionDoneTurnState)
			return new MainAndQuickActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		if (previousState instanceof MainAndQuickActionDoneTurnState) {
			super.getPlayer().additionalMainsToDo--;
			return new MainAndQuickActionDoneTurnState(super.getPlayer().additionalMainsToDo);
		}
		return null;
	}

}