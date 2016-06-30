package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class SendAssistantToElectCouncillorAction extends QuickAction {

	/**
	 * use an assistant to elect a councillor
	 */
	private static final long serialVersionUID = -3938339418005763618L;
	private final ColorCouncillor color;
	private final RegionType regType;

	public SendAssistantToElectCouncillorAction(Integer playerID,
			RegionType regionType, ColorCouncillor color) {
		super(playerID);
		this.color = color;
		this.regType = regionType;
	}

	@Override
	public boolean isValid(Model model) {
		Player player = model.id2player(super.getPlayer());
		Balcony balcony;
		if (regType != null)
			balcony = model.getGameBoard().getRegion(regType).getBalcony();
		else
			balcony = model.getGameBoard().getKing().getBalcony();

		return (player.getAssistants() >= 1
				&& model.getGameBoard().councillorIsAvailable(color)
				&& balcony != null && color != null);
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = model.id2player(super.getPlayer());
		Balcony balcony;
		if (regType != null)
			balcony = model.getGameBoard().getRegion(regType).getBalcony();
		else
			balcony = model.getGameBoard().getKing().getBalcony();

		player.useAssistants(1);
		
		model.getGameBoard().addAssistants(1);
		model.getGameBoard().addDiscardedCouncillor(
				balcony.electCouncillor(color));
		model.getGameBoard().useCouncillor(color);

		if(regType!=null)
			model.getGameBoard().getRegion(regType).setBalcony();
		else
			model.getGameBoard().getKing().setBalcony();

		return nextState(previousState, model);
	}
}
