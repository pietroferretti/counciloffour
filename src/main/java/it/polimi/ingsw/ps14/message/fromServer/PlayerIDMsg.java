package it.polimi.ingsw.ps14.message.fromServer;

import it.polimi.ingsw.ps14.message.Message;

public class PlayerIDMsg implements Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1454222192738815165L;
	private final Integer playerID;

	public PlayerIDMsg(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}

}
