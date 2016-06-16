package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.Player;

public class UpdateThisPlayerMsg implements Message {
	
	private final Integer playerID;

	public UpdateThisPlayerMsg(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}
}
