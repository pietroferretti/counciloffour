package it.polimi.ingsw.ps14.message.fromClient;

import it.polimi.ingsw.ps14.message.Message;

public class NewCurrentPlayerMsg implements Message {
	
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
