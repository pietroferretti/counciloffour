package it.polimi.ingsw.ps14.controller.actions.mainactions;

import it.polimi.ingsw.ps14.Balcony;
import it.polimi.ingsw.ps14.ColorCouncillor;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class ElectCouncillorAction extends MainAction {

	private final ColorCouncillor councillor;
	private final Balcony balcony;

	// chi chiama questo metodo deve ricavare il balcone dalla region o dal king
	public ElectCouncillorAction(Player player, GameBoard gameBoard, ColorCouncillor councillor, Balcony balcony) {
		super(player, gameBoard);
		this.councillor = councillor;
		this.balcony = balcony;
	}

	@Override
	public boolean isValid() {
		return super.getGameBoard().councillorIsAvailable(councillor);
	}

	@Override
	public TurnState execute(TurnState previousState) {
		// electCouncillor return discarded councillor, discarded councillor is
		// added to availableCouncillors in gameboard
		super.getGameBoard().addDiscardedCouncillor(balcony.electCouncillor(councillor));

		// electCouncillor give 4 coins to player
		super.getPlayer().addCoins(4);

		return nextState(previousState);
	}


}
