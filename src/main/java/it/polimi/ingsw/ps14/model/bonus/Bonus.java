package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public abstract class Bonus {

	private final int quantity;

	public Bonus(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public abstract void useBonus(Player player, GameBoard gameboard);
}