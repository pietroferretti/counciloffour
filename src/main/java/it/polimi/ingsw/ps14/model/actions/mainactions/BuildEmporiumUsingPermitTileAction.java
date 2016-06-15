package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class BuildEmporiumUsingPermitTileAction extends MainAction {
	private final Integer businessCardID;
	private final String cityName;

	public BuildEmporiumUsingPermitTileAction(Integer playerID,Integer cardID, String city) {
		super(playerID);
		this.businessCardID = cardID;
		this.cityName=city;
	}

	@Override
	public boolean isValid(Model model) {
		Player player = id2player(super.getPlayer(), model);
		if(player==null) return false;
		BusinessPermit businessCard = id2permit(businessCardID, player);
		if(businessCard==null) return false;
		City city = id2city(cityName, model);
		if(city==null) return false;

		//check if player has this business permit
		if(!player.getBusinessHand().contains(businessCard)) return false;

		//city is in the list of business permit selected
		if(!businessCard.contains(city)) return false;

		//check if player has built in this city yet 
		if(city.isEmporiumBuilt(player)) return false;

		//check if player has money enough to pay players that have built in the city yet
		if(city.numEmporiumsBuilt()>player.getAssistants()) return false;

		return true;
	}


	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = id2player(super.getPlayer(), model);
		BusinessPermit businessCard = id2permit(businessCardID, player);
		City city = id2city(cityName, model);
		
		//use permit
		player.getBusinessHand().usePermit(businessCard);
		
		//give assistant back to gameboard (according to #Emporium built in this city)
		player.useAssistants(city.numEmporiumsBuilt());

		
		//add them to gameboard
		player.addAssistants(city.numEmporiumsBuilt());

		
		//build emporium in the city
		city.buildEmporium(player);
		player.addNumEmporiums(); //count emporium build for each player

		
		//apply city token
		city.getToken().useBonus(player, model);
		
		
		//check bonus neighbors
		useBonusNeighbors(city,player,model);
		
		if (player.getNumEmporiums() == 10) {
			player.addPoints(3);
			return new EndTurnState();
		}
		
		return nextState(previousState,player);
	}

}
