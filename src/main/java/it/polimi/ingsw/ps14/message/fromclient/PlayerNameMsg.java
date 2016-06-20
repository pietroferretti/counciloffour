package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;

public class PlayerNameMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3207087041540300469L;
	private String playerName;

	public PlayerNameMsg(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}
}
