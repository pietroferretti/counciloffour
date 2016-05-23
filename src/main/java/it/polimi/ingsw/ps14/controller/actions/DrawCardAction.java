package it.polimi.ingsw.ps14.controller.actions;

import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.MainActionDoneState;

public class DrawCardAction extends MainAction {

	public DrawCardAction(Player player, GameBoard gameBoard) {
		super(player, gameBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() { // ci sono altri problemi??
		// Check if politicDeck is empty
		if (gameBoard.getPoliticDeck().getDeck().size() == 0)

			// oppure posso risolvere il prolema andando a shuffolare di nuovo
			// gameBoard.getPoliticDeck().shuffleAll();
			// return true;

			return false;
		return true;
	}

	@Override
	public Object execute() {
		player.addPolitic(gameBoard.getPoliticDeck().drawCard());
		return new MainActionDoneState();
	}

}
