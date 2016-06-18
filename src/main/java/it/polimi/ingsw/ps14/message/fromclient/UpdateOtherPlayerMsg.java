package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;

public class UpdateOtherPlayerMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8797985306835133908L;
	private final Integer playerID;

	public UpdateOtherPlayerMsg(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}
}
