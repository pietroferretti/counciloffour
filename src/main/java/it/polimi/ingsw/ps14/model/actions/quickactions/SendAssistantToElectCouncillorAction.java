package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class SendAssistantToElectCouncillorAction extends QuickAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3938339418005763618L;
	private final ColorCouncillor color;
	private final RegionType regType;

	public SendAssistantToElectCouncillorAction(Integer playerID, RegionType regionType, ColorCouncillor color) {
		super(playerID);
		this.color = color;
		this.regType = regionType;
	}

	public boolean isValid(Model model) {
		Player player = id2player(super.getPlayer(), model);
		Balcony balcony = model.getGameBoard().getRegion(regType).getBalcony();

		return (player.getAssistants() >= 1 && model.getGameBoard().councillorIsAvailable(color) && balcony != null
				&& color != null);
	}

	public TurnState execute(TurnState previousState, Model model) {
		Player player = id2player(super.getPlayer(), model);
		Balcony balcony = model.getGameBoard().getRegion(regType).getBalcony();

		player.useAssistants(1);
		model.getGameBoard().addAssistants(1);
		model.getGameBoard().addDiscardedCouncillor(balcony.electCouncillor(color));

		return nextState(previousState, model);
	}
}
