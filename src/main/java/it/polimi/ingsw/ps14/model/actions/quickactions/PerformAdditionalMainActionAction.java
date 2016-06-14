package it.polimi.ingsw.ps14.model.actions.quickactions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class PerformAdditionalMainActionAction extends QuickAction {

	public PerformAdditionalMainActionAction(Integer playerID) {
		super(playerID);
	}
	
	public boolean isValid(Model model) {
		Player player=id2player(getPlayer(), model);
		
		return player.getAssistants() >= 3;
	}
	
	public TurnState execute(TurnState previousState,Model model) {
		Player player=id2player(getPlayer(), model);

		player.useAssistants(3);
		model.getGameBoard().addAssistants(3);
		player.additionalMainsToDo++;
		
		return nextState(previousState,model); 
	}
}
