package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.model.Player;

public class UpdateThisPlayerMsg {
	
	private final Player player;

	public UpdateThisPlayerMsg(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
