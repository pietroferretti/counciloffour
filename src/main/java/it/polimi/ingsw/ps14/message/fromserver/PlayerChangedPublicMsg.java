package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;


public class PlayerChangedPublicMsg implements Message{

	private static final long serialVersionUID = -4322939345181165635L;
	/**
	 * player is changed, the details are visible by all the players
	 */

	private int playerID;
	private String notice;

	public PlayerChangedPublicMsg(int playerID, String notice) {
		this.playerID = playerID;
		this.notice = notice;
	}

	public int getPlayerID() {
		return playerID;
	}
	
	public String getNotice(){
		return notice;
	}

}
