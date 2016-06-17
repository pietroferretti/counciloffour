package it.polimi.ingsw.ps14.message.fromClient;

import it.polimi.ingsw.ps14.message.Message;


public class UpdateOtherPlayerMsg implements Message {

	private final Integer playerID;

	public UpdateOtherPlayerMsg(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}
}
