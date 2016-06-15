package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.Player;

public class UpdateOtherPlayerMsg {

	private final Player player;

	public Player getPlayer() {
		return player;
	}

	public UpdateOtherPlayerMsg(Player player) {
		this.player = player;
	}
}
