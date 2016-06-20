package it.polimi.ingsw.ps14.message.fromserver;

import java.awt.Color;
import java.io.Serializable;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.Player;

public class OtherPlayerUpdateMsg implements Message, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6939039822741889671L;

	private final int id;
	private String name;
	private Color color;
	private int coins;
	private int assistants;
	private int level; // nobility
	private int points;
	private int numEmporiums = 0;

	public OtherPlayerUpdateMsg(Player p) {
		this.id = p.getId();
		this.name = p.getName();
		this.color = p.getColor();
		this.coins = p.getCoins();
		this.assistants = p.getAssistants();
		this.level = p.getLevel();
		this.points = p.getPoints();
		this.numEmporiums = p.getNumEmporiums();
	}

	/**
	 * It prints a specific player's name, color, business permits, assistants
	 * number, nobility level, victory points
	 * 
	 * @param player
	 */

	@Override
	public String toString() {
		return "\nName: " + name + "\nColor: " + color.toString() + "\nCoins: " + Integer.toString(coins)
				+ "\nAssistants: " + Integer.toString(assistants) + "\nNobility level: " + Integer.toString(level)
				+ "\nVictory Points: " + Integer.toString(points);
		// printValidBusinessPermitsPlayer(player.getBusinessHand().getValidCards());
	}

}
