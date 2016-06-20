package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class ChangeBusinessPermitTilesAction extends QuickAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7772522018552870637L;
	private final RegionType regType;

	public ChangeBusinessPermitTilesAction(Integer playerID, RegionType region) {
		super(playerID);
		this.regType = region;
	}

	@Override
	public boolean isValid(Model model) {
		Player player = model.id2player(super.getPlayer());
		Region region = model.getGameBoard().getRegion(regType);

		return region.getBusinessPermits().cardsLeftInDeck() > 0 && player.getAssistants() >= 1 && region != null;
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = model.id2player(super.getPlayer());
		Region region = model.getGameBoard().getRegion(regType);

		player.useAssistants(1);
		model.getGameBoard().addAssistants(1);

		BusinessPermit[] permitArray = region.getBusinessPermits().getAvailablePermits();
		for (BusinessPermit card : permitArray) {
			region.getBusinessPermits().removeBusinessPermit(card);
			region.getBusinessPermits().addCardToDeckBottom(card);
		}
		region.getBusinessPermits().fillEmptySpots();
		region.setBusinessPermits();
		
		return nextState(previousState, model);
	}

}
