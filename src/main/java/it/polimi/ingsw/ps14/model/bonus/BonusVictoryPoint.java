package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusVictoryPoint extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6153823313381004339L;
	private boolean used;

	public BonusVictoryPoint(int quantity) {
		super(quantity);
		used = false;
	}

	@Override
	public void useBonus(Player player, Model model) {

		player.addPoints(super.getQuantity());
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed() {
		used = true;
	}

	@Override
	public String toString() {
		return "+" + Integer.toString(getQuantity()) + " victory points\n";
	}

}
