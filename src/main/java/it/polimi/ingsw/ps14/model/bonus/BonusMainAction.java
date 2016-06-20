package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusMainAction implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6259525072078835164L;

	private final int quantity;

	public BonusMainAction(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	@Override
	public void useBonus(Player player, Model model) {
		for (int i = 0; i < quantity; i++) {
			model.incrementAdditionalMainsToDo();
		}
	}
	
	@Override
	public BonusMainAction makeCopy() {
		return new BonusMainAction(quantity);
	}

	@Override
	public String toString() {
		return "+1 main action";
	}
}
