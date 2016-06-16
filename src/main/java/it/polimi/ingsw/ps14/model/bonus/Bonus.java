package it.polimi.ingsw.ps14.model.bonus;

import java.io.Serializable;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public abstract class Bonus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7017855028316005608L;
	private final int quantity;

	public Bonus(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public abstract void useBonus(Player player, Model model);
}