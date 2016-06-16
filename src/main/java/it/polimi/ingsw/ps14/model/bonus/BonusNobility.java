package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusNobility extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4670449670079163294L;

	public BonusNobility(int quantity) {
		super(quantity);
	}

	// nobility is player.level
	@Override
	public void useBonus(Player player, Model model) {
		player.levelUp(super.getQuantity());
	}

	@Override
	public String toString() {
		return "+" + Integer.toString(getQuantity()) + " nobility points%n";
	}
}
