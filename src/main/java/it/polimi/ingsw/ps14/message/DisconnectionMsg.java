package it.polimi.ingsw.ps14.message;

public class DisconnectionMsg implements Message {

	private int playerID;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1740500634703324420L;

	public DisconnectionMsg(Integer playerID) {
		this.playerID = playerID;
	}

	public int getPlayerID() {
		return playerID;
	}
}
