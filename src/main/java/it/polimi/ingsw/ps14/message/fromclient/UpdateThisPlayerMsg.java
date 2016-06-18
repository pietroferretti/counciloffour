package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;

public class UpdateThisPlayerMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4652633208015243287L;
	private final Integer playerID;

	public UpdateThisPlayerMsg(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}
}
