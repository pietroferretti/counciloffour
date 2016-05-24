package it.polimi.ingsw.ps14.controller.actions.mainactions;

import java.util.ArrayList;

import it.polimi.ingsw.ps14.Balcony;
import it.polimi.ingsw.ps14.GameBoard;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.PoliticCard;
import it.polimi.ingsw.ps14.controller.actions.MainAction;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;

public class AcquireBusinessPermiteTileAction extends MainAction {

	private ArrayList<PoliticCard> cards;
	private Balcony balcony;

	public AcquireBusinessPermiteTileAction(Player player, GameBoard gameBoard, TurnState previousState,
			ArrayList<PoliticCard> cards, Balcony balcony) {
		super(player, gameBoard, previousState);
		// TODO Auto-generated constructor stub
		this.balcony = balcony;
		this.cards = cards;
	}

	@Override
	public boolean isValid() {
		if (!balcony.thereIsColorsInBalcony(cards))
			return false;
		// TODO: send error: ERROR in color choice
		if (super.getPlayer().getCoins() < balcony.councillorCost(cards))
			return false;
		//TODO: send ERROR: not enough coins 
		return true;
	}

	@Override
	public TurnState execute() {
		return null;
	}

	private TurnState nextState(TurnState previousState) {

		return null;
	}

}
