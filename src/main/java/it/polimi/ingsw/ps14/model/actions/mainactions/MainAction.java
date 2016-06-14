package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.actions.TurnAction;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public abstract class MainAction extends TurnAction {

	public MainAction(Integer playerID) {
		super(playerID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(Model model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TurnState execute(TurnState previousState,Model model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void useBonusNeighbors(City city,Player player,Model model) {
		List<City> cityVisited = new ArrayList<>();
		PriorityQueue<City> cityToken = new PriorityQueue<>();
		cityToken.add(city);
		int cont = 1;
		while (cont == 1) {
			city=cityToken.poll();
			cont=0;
			for (City c : city.getNeighbors()) {
				if (c.isEmporiumBuilt(player) && !cityVisited.contains(c) && !cityToken.contains(c)) {
					c.getToken().useBonus(player,model);
					cityToken.add(c);
					cont=1;
				}
			}
			cityVisited.add(city);
		}
	}
	
	protected List<PoliticCard> politicID2cards(List<Integer> politicCardsID ,Player player){
		List<PoliticCard> politicCards=new ArrayList<>();
		for (Integer integer : politicCardsID) 
			for(PoliticCard pc : player.getHand())
				if(integer==pc.getId()) politicCards.add(pc);
		return politicCards;
	}
	
	
	protected BusinessPermit id2permit(Integer permitID, Player player){
		for(BusinessPermit bp : player.getBusinessHand().getValidCards())
			if(bp.getId()==permitID) return bp;
		return null;
	}
	
	protected BusinessPermit id2permit(Integer permitID, Region region){
		for(BusinessPermit bp : region.getBusinessPermits().getAvailablePermits())
			if(bp.getId()==permitID) return bp;
		return null;
	}
	
	protected City id2city(String name, Model model){
		for(City c : model.getGameBoard().getCities())
			if(c.getName().compareTo(name)==0)
				return c;
		return null;
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