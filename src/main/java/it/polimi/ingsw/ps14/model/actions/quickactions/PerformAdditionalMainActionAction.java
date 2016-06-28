package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class PerformAdditionalMainActionAction extends QuickAction {

	/**
	 * the player pay 3 assistant and can do one more main action
	 */
	private static final long serialVersionUID = 4902881250620758906L;

	public PerformAdditionalMainActionAction(Integer playerID) {
		super(playerID);
	}

	@Override
	public boolean isValid(Model model) {
		Player player = model.id2player(getPlayer());

		return player.getAssistants() >= 3;
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = model.id2player(getPlayer());

		player.useAssistants(3);
		model.getGameBoard().addAssistants(3);
		model.incrementAdditionalMainsToDo();

		return nextState(previousState, model);
	}
}
