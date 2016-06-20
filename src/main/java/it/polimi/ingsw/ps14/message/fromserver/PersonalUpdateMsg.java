package it.polimi.ingsw.ps14.message.fromserver;

import java.io.Serializable;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.Player;

public class PersonalUpdateMsg implements Message, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6600488996773878973L;

	private Player player;

	public PersonalUpdateMsg(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public String toString() {
		return "\nPERSONAL DETAILS:\n" + player.toString();
	}

}
