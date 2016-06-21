package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public class PlayerChangedPrivateMsg extends PrivateMessage implements Message{

	/**
	 * player details visible only by the player
	 */
	private static final long serialVersionUID = -2781710980582478572L;
	private String message;

	public PlayerChangedPrivateMsg(int playerID, String message) {
		super(playerID);
		this.message = message;
	}


	public String toString() {
		return message;
	}

}
