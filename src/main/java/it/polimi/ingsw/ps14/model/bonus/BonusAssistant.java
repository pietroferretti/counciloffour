package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusAssistant implements Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4049326617958390157L;

	private final int quantity;

	public BonusAssistant(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	@Override
	public void useBonus(Player player, Model model) {
		model.getGameBoard().useAssistants(quantity);
		player.addAssistants(quantity);
	}
	
	@Override
	public BonusAssistant makeCopy() {
		return new BonusAssistant(quantity);
	}

	@Override
	public String toString() {
		return "+" + Integer.toString(quantity) + " assistant(s)\n";
	}
}
