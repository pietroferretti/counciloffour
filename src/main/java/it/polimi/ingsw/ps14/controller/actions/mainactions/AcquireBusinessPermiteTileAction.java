package it.polimi.ingsw.ps14.controller.actions.mainactions;

import java.util.List;

import it.polimi.ingsw.ps14.Balcony;
import it.polimi.ingsw.ps14.BusinessPermit;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticCard;
import it.polimi.ingsw.ps14.Region;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenAlreadyDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenNotDoneYetTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.DrawnCardState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class AcquireBusinessPermiteTileAction extends MainAction {

	private Region region;
	private BusinessPermit permitTile;
	private Balcony balcony;
	private List<PoliticCard> hand;
	private TurnState previousState;

	public AcquireBusinessPermiteTileAction(Player player, GameBoard gameBoard, TurnState previousState, Region region,BusinessPermit permitTile) {
		super(player, gameBoard, previousState);
		// TODO Auto-generated constructor stub
		this.region = region;
		this.balcony=region.getBalcony();
		this.hand=super.getPlayer().getHand();
		this.permitTile=permitTile;
	}

	@Override
	public boolean isValid() {
		
		if (balcony.cardsInBalcony(hand)==-1)
			return false;
		// TODO: send error: ERROR in color choice
		if (super.getPlayer().getCoins() < balcony.councillorCost(hand))
			return false;
		//TODO: send ERROR: not enough coins 
		if (!region.getBusinessPermits().cardIsChoosable(permitTile))
			return false;
		//TODO: send ERROR: permitTile is different from choosable
		return true;
	}

	@Override
	public TurnState execute() {
		if(super.getPlayer().useCoins(balcony.councillorCost(hand))){
			super.getPlayer().getBusinessHand().acquireBusinessPermit(permitTile);
			region.getBusinessPermits().substituteCard(permitTile);
		}
		return nextState();
	}
	
	
	

	private TurnState nextState() {
		if (previousState instanceof DrawnCardState)
			return MainActionDoneTurnState.getInstance();
		if (previousState instanceof MainActionDoneTurnState)
			return ChooseMainWhenAlreadyDoneTurnState.getInstance();
		if (previousState instanceof QuickActionDoneTurnState)
			return MainAndQuickActionDoneTurnState.getInstance();
		if (previousState instanceof ChooseMainWhenNotDoneYetTurnState)
			return QuickActionDoneTurnState.getInstance();
		return null;
	}


}
