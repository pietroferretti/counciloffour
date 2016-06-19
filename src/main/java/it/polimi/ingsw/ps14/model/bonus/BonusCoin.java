package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusCoin extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269595143314324294L;

	public BonusCoin(int quantity) {
		super(quantity);
	}

	@Override
	public void useBonus(Player player, Model model) {
		player.addCoins(super.getQuantity());
	}

	@Override
	public String toString() {
		return "+" + Integer.toString(getQuantity()) + "coins\n";
	}
}
