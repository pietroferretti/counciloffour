package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusNobilityLvlUp implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4670449670079163294L;

	private final int quantity;

	public BonusNobilityLvlUp(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}
	// nobility is player.level
	@Override
	public void useBonus(Player player, Model model) {
		player.levelUp(quantity);
	}

	@Override
	public BonusNobilityLvlUp makeCopy() {
		return new BonusNobilityLvlUp(quantity);
	}
	
	@Override
	public String toString() {
		return "+" + Integer.toString(quantity) + " nobility points\n";
	}
}
