package it.polimi.ingsw.ps14.model.modelview;

public class PlayerChangedPrivateMsg {

	private int playerID;
	private String message;

	public PlayerChangedPrivateMsg(int playerID, String message) {
		this.playerID = playerID;
		this.message = message;
	}

	public int getPlayerID() {
		return playerID;
	}

}
