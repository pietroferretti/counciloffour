package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.message.fromserver.InfoPrivateMsg;
import it.polimi.ingsw.ps14.message.fromserver.InfoPublicMsg;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
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
		Player player = model.id2player(super.getPlayerID());

		PoliticCard card = model.getGameBoard().getPoliticDeck().drawCard();
		
		if (card == null) {
			model.setMessage(new InfoPublicMsg(String.format("There are no politic cards left on the table!", player.getName())));
			model.setMessage(new InfoPrivateMsg(super.getPlayerID(), "You didn't draw a card, because there are none left."));
		}
		
		player.addPolitic(model.getGameBoard().getPoliticDeck().drawCard());
		
		model.setMessage(new InfoPublicMsg(String.format("%s has drawn a card.", player.getName())));
		
		model.setAdditionalMainsToDo(0); 		
		return new CardDrawnState();
	}

}
