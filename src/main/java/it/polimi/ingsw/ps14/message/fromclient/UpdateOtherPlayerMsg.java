package it.polimi.ingsw.ps14.message.fromclient;

public class UpdateOtherPlayerMsg implements UpdateRequestMsg {

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
