package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.Player;

public class PlayerChangedPrivateMsg extends PrivateMessage implements Message {

	/**
	 * player details visible only by the player
	 */
	private static final long serialVersionUID = -2781710980582478572L;
	private String message;
	private Player player;

	public PlayerChangedPrivateMsg(int playerID, Player player, String message) {
		super(playerID);
		this.message = message;
		this.player = player;
	}

	public String toString() {
		return message;
	}

	public Player getPlayer() {
		return player;
	}

}
