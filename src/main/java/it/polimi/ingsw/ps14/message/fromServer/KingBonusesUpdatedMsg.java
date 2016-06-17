package it.polimi.ingsw.ps14.message.fromServer;

import it.polimi.ingsw.ps14.message.Message;

public class KingBonusesUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6807233797097755280L;

	private int updatedShowableKingBonus;

	public KingBonusesUpdatedMsg(int updatedShowableKingBonus) {
		this.updatedShowableKingBonus = updatedShowableKingBonus;
	}

	public int getUpdatedShowableKingBonus() {
		return updatedShowableKingBonus;
	}

}
