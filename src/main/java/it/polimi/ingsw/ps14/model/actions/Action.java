package it.polimi.ingsw.ps14.model.actions;

import java.io.Serializable;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class Action implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7573631303491595938L;
	private final Integer playerID;

	public Action(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}

	// check if the player can do the action based on the model current state
	public abstract boolean isValid(Model model);

	public abstract TurnState execute(TurnState previousState, Model model);
}
