package it.polimi.ingsw.ps14.controller.actions.mainactions;

import it.polimi.ingsw.ps14.BusinessPermit;
import it.polimi.ingsw.ps14.City;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class BuildEmporiumUsingPermitTileAction extends MainAction {
	private final BusinessPermit businessCard;
	private final City city;

	public BuildEmporiumUsingPermitTileAction(Player player,
			GameBoard gameBoard, BusinessPermit card, City city) {
		super(player, gameBoard);
		this.businessCard = card;
		this.city=city;
	}

	@Override
	public boolean isValid() {
		//check if player has this business permit
		if(!super.getPlayer().getBusinessHand().contains(businessCard)) return false;
		
		//city is in the list of business permit selected
		if(!businessCard.contains(city)) return false;
		
		//check if player has built in this city yet 
		if(city.isEmporiumBuilt(super.getPlayer())) return false;
		
		//check if player has money enough to pay players that have built in the city yet
		if(city.numEmporiumsBuilt()>super.getPlayer().getAssistants()) return false;
		
		return true;
	}


	@Override
	public TurnState execute(TurnState previousState) {
		
		//use permit
		super.getPlayer().getBusinessHand().usePermit(businessCard);
		
		//give assistant back to gameboard (according to #Emporium built in this city)
		super.getPlayer().useAssistants(city.numEmporiumsBuilt());
		
		//add them to gameboard
		super.getGameBoard().addAssistants(city.numEmporiumsBuilt());
		
		//build emporium in the city
		city.buildEmporium(super.getPlayer());
		
		//apply city token
		city.getToken().useBonus(super.getPlayer(), super.getGameBoard().getPoliticDeck());
		
		//check city neighbours token
		//????????????????????
		
		return nextState(previousState);
	}

}
