package it.polimi.ingsw.ps14.model.actions.mainactions;

import it.polimi.ingsw.ps14.model.Balcony;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

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
		int electCouncillorPrize=4;
		// electCouncillor return discarded councillor, discarded councillor is
		// added to availableCouncillors in gameboard
		super.getGameBoard().addDiscardedCouncillor(balcony.electCouncillor(councillor));

		// electCouncillor give 4 coins to player
		super.getPlayer().addCoins(electCouncillorPrize);

		return nextState(previousState);
	}


}