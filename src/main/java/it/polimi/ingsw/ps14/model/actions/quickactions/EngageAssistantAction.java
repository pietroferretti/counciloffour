package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class EngageAssistantAction extends QuickAction {

	public EngageAssistantAction(Integer playerID) {
		super(playerID);
	}

	public boolean isValid(Model model) {
		Player player = id2player(getPlayer(), model);

		return (model.getGameBoard().getAvailableAssistants() >= 1 && player
				.getCoins() >= 3);
	}

	public TurnState execute(TurnState previousState, Model model) {
		Player player = id2player(getPlayer(), model);

		player.useCoins(3);
		model.getGameBoard().useAssistants(1);
		player.addAssistants(1);

		return nextState(previousState, model);
	}
}
