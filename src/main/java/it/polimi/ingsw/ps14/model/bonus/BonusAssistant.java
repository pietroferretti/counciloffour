package it.polimi.ingsw.ps14.model.bonus;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class BonusAssistant extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4049326617958390157L;

	public BonusAssistant(int quantity) {
		super(quantity);
	}

	@Override
	public void useBonus(Player player, Model model) {
		model.getGameBoard().useAssistants(super.getQuantity());
		player.addAssistants(super.getQuantity());
	}

	@Override
	public String toString() {
		return "+" + Integer.toString(getQuantity()) + " assistant(s)\n";
	}
}
