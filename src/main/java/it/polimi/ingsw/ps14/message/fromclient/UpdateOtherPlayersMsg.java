package it.polimi.ingsw.ps14.message.fromclient;

public class UpdateOtherPlayersMsg implements UpdateRequestMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8797985306835133908L;

	private final Integer playerID;

	/**
	 * 
	 * @param playerID
	 *            Player sending the request.
	 */
	public UpdateOtherPlayersMsg(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}
}
