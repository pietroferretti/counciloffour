package it.polimi.ingsw.ps14.model.modelview;

public class PlayerChangedPublicMsg {

	private int playerID;
	private String notice;

	public PlayerChangedPublicMsg(int playerID, String notice) {
		this.playerID = playerID;
		this.notice = notice;
	}

	public int getPlayerID() {
		return playerID;
	}

}
