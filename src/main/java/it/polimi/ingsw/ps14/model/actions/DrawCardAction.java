package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.message.fromserver.InfoPublicMsg;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class DrawCardAction extends TurnAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4399052017170395915L;

	public DrawCardAction(Integer playerID) {
		super(playerID);
	}

	@Override
	public boolean isValid(Model model) {
		// always valid :D
		return true;
	}

	@Override
	public TurnState execute(TurnState previousState, Model model) {
		Player player = model.id2player(super.getPlayer());

		player.addPolitic(model.getGameBoard().getPoliticDeck().drawCard());
		
		model.setMessage(new InfoPublicMsg(String.format("%s has drawn a card.", player.getName())));
		
		model.setAdditionalMainsToDo(0); 		
		return new CardDrawnState();
	}

}
