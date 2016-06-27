package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;

public class MyChatMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7805337572521767421L;

	private String text;
	private int playerID;

	public MyChatMsg(String text, int playerID) {
		super();
		this.text = text;
		this.playerID = playerID;
	}

	public String getText() {
		return text;
	}

	public int getPlayerID() {
		return playerID;
	}

}
