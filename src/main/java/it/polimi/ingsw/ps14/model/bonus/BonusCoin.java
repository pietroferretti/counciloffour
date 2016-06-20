package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusCoin implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269595143314324294L;
	
	private final int quantity;

	public BonusCoin(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	@Override
	public void useBonus(Player player, Model model) {
		player.addCoins(quantity);
	}
	
	@Override
	public BonusCoin makeCopy() {
		return new BonusCoin(quantity);
	}

	@Override
	public String toString() {
		return "\n+" + Integer.toString(quantity) + "coins";
	}
}
