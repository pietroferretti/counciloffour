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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getCoins() {
		return coins;
	}

	public int getAssistants() {
		return assistants;
	}

	public int getLevel() {
		return level;
	}

	public int getPoints() {
		return points;
	}

	public int getNumEmporiums() {
		return numEmporiums;
	}

}
