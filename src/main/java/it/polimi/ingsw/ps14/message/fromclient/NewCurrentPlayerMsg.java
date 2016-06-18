package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;

public class NewCurrentPlayerMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3207087041540300469L;
	private String playerName;
	private int playerID;

	public NewCurrentPlayerMsg(String playerName, int playerID) {
		this.playerName = playerName;
		this.playerID = playerID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}
}
