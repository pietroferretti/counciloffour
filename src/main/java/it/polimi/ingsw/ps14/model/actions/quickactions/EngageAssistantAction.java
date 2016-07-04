package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class EngageAssistantAction extends QuickAction {

	/**
	 * engage an assistant: the player pay 3 coins to get an assistant from the gameboard
	 */
	private static final long serialVersionUID = 8330396498177010120L;

	public EngageAssistantAction(Integer playerID) {
		super(playerID);
	}

	@Override
	public boolean isValid(Model model) {
		Player player = model.id2player(getPlayerID());

		return (model.getGameBoard().getAvailableAssistants() >= 1 && player.getCoins() >= 3);
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = model.id2player(getPlayerID());

		player.useCoins(3);
		model.getGameBoard().useAssistants(1);
		player.addAssistants(1);

		return nextState(previousState, model);
	}
}
