package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusMainAction extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6259525072078835164L;

	public BonusMainAction(int quantity) {
		super(quantity);
	}

	@Override
	public void useBonus(Player player, Model model) {
		for (int i = 0; i < super.getQuantity(); i++) {
			player.additionalMainsToDo++;
		}
	}

	@Override
	public String toString() {
		return "+1 main action";
	}
}
