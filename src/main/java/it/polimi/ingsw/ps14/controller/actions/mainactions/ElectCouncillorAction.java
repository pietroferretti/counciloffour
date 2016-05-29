package it.polimi.ingsw.ps14.controller.actions.mainactions;

import it.polimi.ingsw.ps14.Balcony;
import it.polimi.ingsw.ps14.ColorCouncillor;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenAlreadyDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.ChooseMainWhenNotDoneYetTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.DrawnCardState;
import it.polimi.ingsw.ps14.controller.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class ElectCouncillorAction extends MainAction {

	private final ColorCouncillor councillor;
	private final Balcony balcony;

	// chi chiama questo metodo deve ricavare il balcone dalla region o dal king
	public ElectCouncillorAction(Player player, GameBoard gameBoard, ColorCouncillor councillor, Balcony balcony,
			TurnState previousState) {
		super(player, gameBoard, previousState);
		this.councillor = councillor;
		this.balcony = balcony;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid() {
		// this action is always valid
		if (super.getGameBoard().councillorIsAvailable(councillor))
			return true;
		else
			return false;
	}

	@Override
	public TurnState execute() {
		// electCouncillor return discarded councillor, discarded councillor is
		// added to availableCouncillors in gameboard
		super.getGameBoard().addDiscardedCouncillor(balcony.electCouncillor(councillor));

		// electCouncillor give 4 coins to player
		super.getPlayer().addCoins(4);

		return nextState(super.getPreviousState());
	}

	private TurnState nextState(TurnState previousState) {
		if (previousState instanceof DrawnCardState)
			return MainActionDoneTurnState.getInstance();
		if (previousState instanceof ChooseMainWhenNotDoneYetTurnState)
			return QuickActionDoneTurnState.getInstance();
		if ((previousState instanceof QuickActionDoneTurnState)
			|| (previousState instanceof ChooseMainWhenAlreadyDoneTurnState))
			return MainAndQuickActionDoneTurnState.getInstance();
		return null;
	}
}
