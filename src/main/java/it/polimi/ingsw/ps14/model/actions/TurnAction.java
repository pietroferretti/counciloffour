package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public abstract class TurnAction extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 595746578546675225L;

	public TurnAction(Integer playerID) {
		super(playerID);
	}

	protected Player id2player(Integer id, Model model) {
		for (Player p : model.getPlayers())
			if (p.getId() == id)
				return p;
		return null;
	}

}
