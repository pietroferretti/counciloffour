package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class Action {

	private final Integer playerID;

	public Action(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayer() {
		return playerID;
	}

	// check if the player can do the action based on his "attributes"
	public abstract boolean isValid(Model model);

	public abstract TurnState execute(TurnState previousState, Model model);
}
